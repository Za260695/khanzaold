/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package simrskhanza;

import kepegawaian.DlgCariDokter;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Timer;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import rekammedis.RMCariHasilLaborat;
import rekammedis.RMCariHasilRadiologi;
import rekammedis.RMCariJumlahObat;


/**
 *
 * @author perpustakaan
 */
public final class DlgRujuk extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private RMCariHasilRadiologi cariradiologi=new RMCariHasilRadiologi(null,false);
    private RMCariHasilLaborat carilaborat=new RMCariHasilLaborat(null,false);
    private RMCariJumlahObat cariobat=new RMCariJumlahObat(null,false);
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private String diagnosa="",diagnosa2="",keluar="",status="",tgl="",sql="";
    private PreparedStatement psobat,ps;
    private ResultSet rs;
    private int i=0;
    private Date date = new Date();
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgRujuk(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);


        Object[] row={
            "No.Rujuk","No.Rawat","No.R.M.","Nama Pasien","Tgl.Rujuk","Jam Rujuk","RS Tujuan","Bagian","Kategori Rujuk","Ambulan","Keterangan","No Kendaraan",
            "Alasan Rujuk","Pendamping","Nama Pendamping","Keadaan Umum","GCS","RR","Suhu","Tekanan Darah","Nadi","SpO2","Riwayat Alergi","Pemeriksaan Fisik",
            "Pemeriksaan Radiologi","Pemeriksaan Lab","Terapi","Lainnya","Tindakan","Diagnosa","Kode Dokter","Nama Dokter"
            
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

       // tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 30; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(120);
            //    column.setMinWidth(0);
            //    column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(90);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(150);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRj.setDocument(new batasInput((byte)10).getKata(TNoRj));
        TTmpRujuk.setDocument(new batasInput((byte)45).getKata(TTmpRujuk));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TDiagnosa.setDocument(new batasInput((int)5000).getKata(TDiagnosa));
        ket.setDocument(new batasInput((int)5000).getKata(ket));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdDok.setDocument(new batasInput((byte)20).getKata(KdDok));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        jam();
        
        cariradiologi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariradiologi.getTable().getSelectedRow()!= -1){
                    PemeriksaanPj.append(cariradiologi.getTable().getValueAt(cariradiologi.getTable().getSelectedRow(),2).toString()+", ");
                    PemeriksaanPj.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        carilaborat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carilaborat.getTable().getSelectedRow()!= -1){
                    PemeriksaanLab.append(carilaborat.getTable().getValueAt(carilaborat.getTable().getSelectedRow(),2).toString()+", ");
                    PemeriksaanLab.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        cariobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariobat.getTable().getSelectedRow()!= -1){
                    Terapi.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(),2).toString()+", ");
                    Terapi.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }   
                KdDok.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
    }

    private DlgCariDokter dokter=new DlgCariDokter(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSuratRujukan = new javax.swing.JMenuItem();
        MnSuratRujukan2 = new javax.swing.JMenuItem();
        MnSuratRujukan3 = new javax.swing.JMenuItem();
        MnSuratRujukan4 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRj = new widget.TextBox();
        jLabel8 = new widget.Label();
        TTmpRujuk = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TDiagnosa = new widget.TextBox();
        jLabel9 = new widget.Label();
        TPasien = new widget.TextBox();
        DTPRujuk = new widget.Tanggal();
        jLabel12 = new widget.Label();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        btnDokter = new widget.Button();
        TDokter = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        ket = new widget.TextBox();
        jLabel11 = new widget.Label();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        ambulance = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        CmbDetik = new widget.ComboBox();
        ktrujuk = new widget.ComboBox();
        Bagian = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel10 = new widget.Label();
        jLabel17 = new widget.Label();
        Ku = new widget.TextBox();
        jLabel18 = new widget.Label();
        Gcs = new widget.TextBox();
        jLabel20 = new widget.Label();
        Td = new widget.TextBox();
        jLabel22 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel23 = new widget.Label();
        Rr = new widget.TextBox();
        jLabel24 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel25 = new widget.Label();
        Spo = new widget.TextBox();
        jLabel26 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel27 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        Pemeriksaan = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel28 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        btnTerapi = new widget.Button();
        btnPemeriksaanPj = new widget.Button();
        scrollPane3 = new widget.ScrollPane();
        PemeriksaanPj = new widget.TextArea();
        btnPemeriksaanLab = new widget.Button();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanLab = new widget.TextArea();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        NoKendaraan = new widget.TextBox();
        alasanrujuk = new widget.ComboBox();
        jLabel37 = new widget.Label();
        pendamping = new widget.ComboBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        namapendamping = new widget.TextBox();
        scrollPane6 = new widget.ScrollPane();
        Lainnya = new widget.TextArea();
        jLabel40 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Tindakan = new widget.TextArea();
        btnTerapi1 = new widget.Button();
        jLabel41 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSuratRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratRujukan.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratRujukan.setText("Cetak Surat Rujukan 1");
        MnSuratRujukan.setName("MnSuratRujukan"); // NOI18N
        MnSuratRujukan.setPreferredSize(new java.awt.Dimension(250, 26));
        MnSuratRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratRujukanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSuratRujukan);

        MnSuratRujukan2.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratRujukan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratRujukan2.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratRujukan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratRujukan2.setText("Cetak Surat Rujukan 2");
        MnSuratRujukan2.setName("MnSuratRujukan2"); // NOI18N
        MnSuratRujukan2.setPreferredSize(new java.awt.Dimension(250, 26));
        MnSuratRujukan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratRujukan2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSuratRujukan2);

        MnSuratRujukan3.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratRujukan3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratRujukan3.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratRujukan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratRujukan3.setText("Cetak Surat Rujukan 3 (RJ)");
        MnSuratRujukan3.setName("MnSuratRujukan3"); // NOI18N
        MnSuratRujukan3.setPreferredSize(new java.awt.Dimension(250, 26));
        MnSuratRujukan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratRujukan3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSuratRujukan3);

        MnSuratRujukan4.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratRujukan4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratRujukan4.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratRujukan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratRujukan4.setText("Cetak Surat Rujukan Neonatal");
        MnSuratRujukan4.setName("MnSuratRujukan4"); // NOI18N
        MnSuratRujukan4.setPreferredSize(new java.awt.Dimension(250, 26));
        MnSuratRujukan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratRujukan4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSuratRujukan4);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Rujukan Keluar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setColumnSelectionAllowed(true);
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.setRowSelectionAllowed(false);
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rujuk :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(452, 300));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 500));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rujuk :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(680, 10, 60, 23);

        TNoRj.setHighlighter(null);
        TNoRj.setName("TNoRj"); // NOI18N
        TNoRj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRjKeyPressed(evt);
            }
        });
        FormInput.add(TNoRj);
        TNoRj.setBounds(750, 10, 140, 23);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(10, 40, 60, 23);

        TTmpRujuk.setHighlighter(null);
        TTmpRujuk.setName("TTmpRujuk"); // NOI18N
        TTmpRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTmpRujukKeyPressed(evt);
            }
        });
        FormInput.add(TTmpRujuk);
        TTmpRujuk.setBounds(510, 40, 250, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 72, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(75, 10, 141, 23);

        TDiagnosa.setHighlighter(null);
        TDiagnosa.setName("TDiagnosa"); // NOI18N
        TDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(100, 440, 312, 23);

        jLabel9.setText("Diagnosa :");
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(20, 440, 72, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(340, 10, 340, 23);

        DTPRujuk.setForeground(new java.awt.Color(50, 70, 50));
        DTPRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2024" }));
        DTPRujuk.setDisplayFormat("dd-MM-yyyy");
        DTPRujuk.setName("DTPRujuk"); // NOI18N
        DTPRujuk.setOpaque(false);
        DTPRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPRujukKeyPressed(evt);
            }
        });
        FormInput.add(DTPRujuk);
        DTPRujuk.setBounds(80, 40, 100, 23);

        jLabel12.setText("Bagian:");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(760, 40, 50, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(218, 10, 120, 23);

        jLabel5.setText("Keadaan Waktu Dikirim");
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 130, 130, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        FormInput.add(KdDok);
        KdDok.setBounds(480, 440, 100, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("Alt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(770, 440, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        TDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDokterKeyPressed(evt);
            }
        });
        FormInput.add(TDokter);
        TDokter.setBounds(590, 440, 180, 23);

        jLabel13.setText(" Kategori Rujuk :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 70, 90, 23);

        jLabel14.setText(" Ambulance :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(280, 70, 90, 23);

        jLabel15.setText(" Keterangan :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(560, 70, 70, 23);

        ket.setHighlighter(null);
        ket.setName("ket"); // NOI18N
        ket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ketKeyPressed(evt);
            }
        });
        FormInput.add(ket);
        ket.setBounds(640, 70, 170, 23);

        jLabel11.setText("Jam :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(190, 40, 30, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        FormInput.add(CmbJam);
        CmbJam.setBounds(220, 40, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        FormInput.add(CmbMenit);
        CmbMenit.setBounds(290, 40, 62, 23);

        ambulance.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "AGD", "SENDIRI", "SWASTA" }));
        ambulance.setName("ambulance"); // NOI18N
        ambulance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ambulanceKeyPressed(evt);
            }
        });
        FormInput.add(ambulance);
        ambulance.setBounds(380, 70, 170, 23);

        ChkJln.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 195)));
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        FormInput.add(ChkJln);
        ChkJln.setBounds(420, 40, 23, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        FormInput.add(CmbDetik);
        CmbDetik.setBounds(360, 40, 62, 23);

        ktrujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Bedah", "Non Bedah", "Kebidanan", "Anak", "Bayi" }));
        ktrujuk.setName("ktrujuk"); // NOI18N
        ktrujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ktrujukKeyPressed(evt);
            }
        });
        FormInput.add(ktrujuk);
        ktrujuk.setBounds(100, 70, 170, 23);

        Bagian.setHighlighter(null);
        Bagian.setName("Bagian"); // NOI18N
        Bagian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BagianKeyPressed(evt);
            }
        });
        FormInput.add(Bagian);
        Bagian.setBounds(820, 40, 110, 23);

        jLabel16.setText("RS Tujuan:");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(450, 40, 60, 23);

        jLabel10.setText("Keadaan Umum:");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 150, 90, 23);

        jLabel17.setText("Dokter :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(420, 440, 40, 23);

        Ku.setName("Ku"); // NOI18N
        FormInput.add(Ku);
        Ku.setBounds(100, 150, 130, 24);

        jLabel18.setText("GCS:");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 180, 90, 23);

        Gcs.setName("Gcs"); // NOI18N
        FormInput.add(Gcs);
        Gcs.setBounds(100, 180, 130, 24);

        jLabel20.setText("Tindakan  :");
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(820, 310, 70, 23);

        Td.setName("Td"); // NOI18N
        FormInput.add(Td);
        Td.setBounds(500, 150, 70, 24);

        jLabel22.setText("Nadi:");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(440, 180, 50, 23);

        Nadi.setName("Nadi"); // NOI18N
        FormInput.add(Nadi);
        Nadi.setBounds(500, 180, 70, 24);

        jLabel23.setText("RR:");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(240, 150, 30, 23);

        Rr.setName("Rr"); // NOI18N
        FormInput.add(Rr);
        Rr.setBounds(290, 150, 50, 24);

        jLabel24.setText("Suhu:");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(240, 180, 30, 23);

        Suhu.setName("Suhu"); // NOI18N
        FormInput.add(Suhu);
        Suhu.setBounds(290, 180, 50, 24);

        jLabel25.setText("%");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(810, 150, 20, 23);

        Spo.setName("Spo"); // NOI18N
        FormInput.add(Spo);
        Spo.setBounds(720, 150, 90, 24);

        jLabel26.setText("Riwayat Alergi:");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(630, 180, 80, 23);

        Alergi.setName("Alergi"); // NOI18N
        FormInput.add(Alergi);
        Alergi.setBounds(720, 180, 130, 24);

        jLabel27.setText("C");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(350, 180, 10, 23);

        scrollPane1.setName("scrollPane1"); // NOI18N

        Pemeriksaan.setColumns(20);
        Pemeriksaan.setRows(5);
        Pemeriksaan.setName("Pemeriksaan"); // NOI18N
        scrollPane1.setViewportView(Pemeriksaan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(20, 230, 340, 80);

        scrollPane2.setName("scrollPane2"); // NOI18N

        Terapi.setColumns(20);
        Terapi.setRows(5);
        Terapi.setName("Terapi"); // NOI18N
        scrollPane2.setViewportView(Terapi);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(410, 330, 370, 80);

        jLabel28.setText("Terapi:");
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(410, 310, 50, 23);

        jLabel29.setText("Tekanan Darah:");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(410, 150, 80, 23);

        jLabel30.setText("mmHg");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(580, 150, 30, 23);

        jLabel31.setText("kali/menit");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(580, 180, 50, 23);

        jLabel32.setText("kali/menit");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(350, 150, 50, 23);

        jLabel33.setText("SpO2:");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(670, 150, 40, 23);

        btnTerapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTerapi.setMnemonic('2');
        btnTerapi.setToolTipText("Alt+2");
        btnTerapi.setName("btnTerapi"); // NOI18N
        btnTerapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerapiActionPerformed(evt);
            }
        });
        FormInput.add(btnTerapi);
        btnTerapi.setBounds(790, 360, 28, 23);

        btnPemeriksaanPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPemeriksaanPj.setMnemonic('2');
        btnPemeriksaanPj.setToolTipText("Alt+2");
        btnPemeriksaanPj.setName("btnPemeriksaanPj"); // NOI18N
        btnPemeriksaanPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPemeriksaanPjActionPerformed(evt);
            }
        });
        FormInput.add(btnPemeriksaanPj);
        btnPemeriksaanPj.setBounds(360, 360, 28, 23);

        scrollPane3.setName("scrollPane3"); // NOI18N

        PemeriksaanPj.setColumns(20);
        PemeriksaanPj.setRows(5);
        PemeriksaanPj.setName("PemeriksaanPj"); // NOI18N
        scrollPane3.setViewportView(PemeriksaanPj);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(20, 330, 340, 80);

        btnPemeriksaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPemeriksaanLab.setMnemonic('2');
        btnPemeriksaanLab.setToolTipText("Alt+2");
        btnPemeriksaanLab.setName("btnPemeriksaanLab"); // NOI18N
        btnPemeriksaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPemeriksaanLabActionPerformed(evt);
            }
        });
        FormInput.add(btnPemeriksaanLab);
        btnPemeriksaanLab.setBounds(790, 260, 28, 23);

        scrollPane4.setName("scrollPane4"); // NOI18N

        PemeriksaanLab.setColumns(20);
        PemeriksaanLab.setRows(5);
        PemeriksaanLab.setName("PemeriksaanLab"); // NOI18N
        scrollPane4.setViewportView(PemeriksaanLab);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(410, 230, 370, 80);

        jLabel34.setText("Anamnesa :");
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(-40, 210, 130, 23);

        jLabel35.setText("Pemeriksaan Radiologi:");
        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(20, 310, 140, 23);

        jLabel36.setText("No. Kendaraan:");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(10, 100, 80, 23);

        NoKendaraan.setHighlighter(null);
        NoKendaraan.setName("NoKendaraan"); // NOI18N
        NoKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoKendaraanActionPerformed(evt);
            }
        });
        NoKendaraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKendaraanKeyPressed(evt);
            }
        });
        FormInput.add(NoKendaraan);
        NoKendaraan.setBounds(100, 100, 100, 23);

        alasanrujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Perawatan Khusus", "Non Clinical", "Tempat tidur penuh", "Permintaan pasien atau keluarga", "Lainnya" }));
        alasanrujuk.setName("alasanrujuk"); // NOI18N
        alasanrujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alasanrujukActionPerformed(evt);
            }
        });
        alasanrujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                alasanrujukKeyPressed(evt);
            }
        });
        FormInput.add(alasanrujuk);
        alasanrujuk.setBounds(300, 100, 170, 23);

        jLabel37.setText("Alasan dirujuk:");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(210, 100, 80, 23);

        pendamping.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Dokter", "Perawat", "Bidan", "Keluarga" }));
        pendamping.setName("pendamping"); // NOI18N
        pendamping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendampingActionPerformed(evt);
            }
        });
        pendamping.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pendampingKeyPressed(evt);
            }
        });
        FormInput.add(pendamping);
        pendamping.setBounds(560, 100, 170, 23);

        jLabel38.setText("Pendamping:");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(480, 100, 70, 23);

        jLabel39.setText("Nama Pendamping:");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(740, 100, 100, 23);

        namapendamping.setHighlighter(null);
        namapendamping.setName("namapendamping"); // NOI18N
        namapendamping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namapendampingActionPerformed(evt);
            }
        });
        namapendamping.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                namapendampingKeyPressed(evt);
            }
        });
        FormInput.add(namapendamping);
        namapendamping.setBounds(850, 100, 140, 23);

        scrollPane6.setName("scrollPane6"); // NOI18N

        Lainnya.setColumns(20);
        Lainnya.setRows(5);
        Lainnya.setName("Lainnya"); // NOI18N
        scrollPane6.setViewportView(Lainnya);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(820, 230, 300, 80);

        jLabel40.setText("Pemeriksaan Lab:");
        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(400, 210, 110, 23);

        scrollPane7.setName("scrollPane7"); // NOI18N

        Tindakan.setColumns(20);
        Tindakan.setRows(5);
        Tindakan.setName("Tindakan"); // NOI18N
        scrollPane7.setViewportView(Tindakan);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(820, 330, 300, 80);

        btnTerapi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTerapi1.setMnemonic('2');
        btnTerapi1.setToolTipText("Alt+2");
        btnTerapi1.setName("btnTerapi1"); // NOI18N
        btnTerapi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerapi1ActionPerformed(evt);
            }
        });
        FormInput.add(btnTerapi1);
        btnTerapi1.setBounds(1120, 370, 28, 23);

        jLabel41.setText("Keterangan Untuk bayi :");
        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(810, 210, 150, 23);

        Scroll1.setViewportView(FormInput);

        PanelInput.add(Scroll1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRjKeyPressed
        Valid.pindah(evt,TCari,DTPRujuk);
}//GEN-LAST:event_TNoRjKeyPressed

    private void TTmpRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTmpRujukKeyPressed
        Valid.pindah(evt,CmbDetik,ket);
}//GEN-LAST:event_TTmpRujukKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TTmpRujuk,TDiagnosa);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDiagnosaKeyPressed
        Valid.pindah(evt,ket,ktrujuk);
}//GEN-LAST:event_TDiagnosaKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TDokter,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if(TNoRj.getText().trim().equals("")){
            Valid.textKosong(TNoRj,"No.Rujuk");
        }else if(TTmpRujuk.getText().trim().equals("")){
            Valid.textKosong(TTmpRujuk,"tempat rujuk");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(KdDok,"dokter yang bertugas");
        }else{
            Sequel.menyimpan("rujuk","'"+TNoRj.getText()+"','"+
                    TNoRw.getText()+"','"+
                    Valid.SetTgl(DTPRujuk.getSelectedItem()+"")+"','"+ 
                    TTmpRujuk.getText()+"','"+
                    Bagian.getText()+"','"+
                    ktrujuk.getSelectedItem()+"','"+
                    ambulance.getSelectedItem()+ "','"+
                    ket.getText()+"','"+
                    NoKendaraan.getText()+"','"+
                    alasanrujuk.getSelectedItem()+ "','"+
                    pendamping.getSelectedItem()+ "','"+
                    namapendamping.getText()+"','"+
                    Ku.getText()+"','"+
                    Gcs.getText()+"','"+
                    Rr.getText()+"','"+
                    Suhu.getText()+"','"+
                    Td.getText()+"','"+
                    Nadi.getText()+"','"+
                    Spo.getText()+"','"+
                    Alergi.getText()+"','"+
                    Pemeriksaan.getText()+"','"+
                    PemeriksaanPj.getText()+"','"+
                    PemeriksaanLab.getText()+"','"+
                    Terapi.getText()+"','"+
                    Lainnya.getText()+"','"+
                    Tindakan.getText()+"','"+
                    TDiagnosa.getText()+"','"+
                    KdDok.getText()+"','"+
                    CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem()+"'","No.Rujuk");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,ambulance,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,TNoRj,"rujuk","no_rujuk");
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRj.getText().trim().equals("")){
            Valid.textKosong(TNoRj,"No.Rujuk");
        }else if(TTmpRujuk.getText().trim().equals("")){
            Valid.textKosong(TTmpRujuk,"tempat rujuk");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(KdDok,"dokter yang bertugas");
        }else{         
                    Valid.editTable(tabMode,"rujuk","no_rujuk",TNoRj,"no_rawat='"+TNoRw.getText()+
                    "',tgl_rujuk='"+Valid.SetTgl(DTPRujuk.getSelectedItem()+"")+
                    "',rujuk_ke='"+TTmpRujuk.getText()+
                    "',bagian='"+ 
                    "',kat_rujuk='"+ktrujuk.getSelectedItem().toString()+
                    "',ambulance='"+ambulance.getSelectedItem().toString()+ 
                    "',keterangan='"+ket.getText()+
                    "',nokendaraan='"+NoKendaraan.getText()+
                    "',alasanrujuk='"+alasanrujuk.getSelectedItem().toString()+
                    "',pendamping='"+pendamping.getSelectedItem().toString()+
                    "',namapendamping='"+namapendamping.getText()+
                    "',ku='"+Ku.getText()+
                    "',gcs='"+Gcs.getText()+
                    "',respirasi='"+Rr.getText()+
                    "',suhu='"+Suhu.getText()+
                    "',tensi='"+Td.getText()+
                    "',nadi='"+Nadi.getText()+
                    "',spo2='"+Spo.getText()+
                    "',alergi='"+Alergi.getText()+
                    "',pemeriksaan='"+Pemeriksaan.getText()+ 
                    "',pemeriksaanpj='"+PemeriksaanPj.getText()+ 
                    "',pemeriksaanlab='"+PemeriksaanLab.getText()+ 
                    "',terapi='"+Terapi.getText()+ 
                    "',lainnya='"+Lainnya.getText()+ 
                    "',tindakan='"+Tindakan.getText()+ 
                    "',keterangan_diagnosa='"+TDiagnosa.getText()+ 
                    "',kd_dokter='"+KdDok.getText()+
                    "',jam='"+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem()+"'");
            if(tabMode.getRowCount()!=0){tampil();}
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            String tgl=" rujuk.tgl_rujuk between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            Valid.MyReportqry("rptRujuk.jasper","report","::[ Data Rujuk Pasien ]::","select rujuk.no_rujuk,rujuk.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "rujuk.rujuk_ke,rujuk.tgl_rujuk,rujuk.jam,rujuk.keterangan_diagnosa,rujuk.kd_dokter,dokter.nm_dokter,rujuk.kat_rujuk,rujuk.ambulance,rujuk.keterangan "+
                "from rujuk inner join reg_periksa inner join pasien inner join dokter "+
                "on rujuk.no_rawat=reg_periksa.no_rawat "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and rujuk.kd_dokter=dokter.kd_dokter "+
                "where "+tgl+"and no_rujuk like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.rujuk_ke like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.tgl_rujuk like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.keterangan_diagnosa like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                " order by rujuk.no_rujuk",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void DTPRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPRujukKeyPressed
        Valid.pindah(evt,TNoRj,CmbJam);
}//GEN-LAST:event_DTPRujukKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{            
            Valid.pindah(evt,ktrujuk,ambulance);
        }
}//GEN-LAST:event_KdDokKeyPressed

private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_btnDokterActionPerformed

private void TDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDokterKeyPressed
        //Valid.pindah(evt,TKd,TSpek);
}//GEN-LAST:event_TDokterKeyPressed

    private void MnSuratRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratRujukanActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            diagnosa="";
            keluar="";
            try {
                psobat=koneksi.prepareStatement("select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                       "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.nama_brng ");            
                
                try{   
                    psobat.setString(1,TNoRw.getText());
                    rs=psobat.executeQuery();
                    while(rs.next()){
                        if(diagnosa.equals("")){
                            diagnosa=rs.getString(1);
                        }else {
                            diagnosa=diagnosa+", "+rs.getString(1);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }

            diagnosa2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="rawat inap";
                }else {
                    diagnosa2=diagnosa2+", rawat inap";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan laboratorium";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan laboratorium";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan radiologi";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan radiologi";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="operasi";
                }else {
                    diagnosa2=diagnosa2+", operasi";
                }
            }

            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());

            param.put("html","Demikianlah riwayat perawatan selama di "+akses.getnamars()+" dengan diagnosa akhir "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+". "+
                "Atas kerjasamanya kami ucapkan terima kasih");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("diagnosa",tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            param.put("tindakan",diagnosa2);
            param.put("terpi",diagnosa);
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSuratRujukan.jasper","report","::[ Surat Rujukan ]::",
                "select rujuk.keterangan_diagnosa,rujuk.alergi,rujuk.rujuk_ke,rujuk.no_rujuk,reg_periksa.no_rawat,pasien.alamat,dokter.nm_dokter,rujuk.suhu,rujuk.tensi,rujuk.nadi,rujuk.spo2,rujuk.pemeriksaan, "+
                "reg_periksa.no_rkm_medis,pasien.jk,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.nm_pasien,rujuk.pemeriksaanpj,rujuk.pemeriksaanlab,rujuk.terapi,"+
                "reg_periksa.almt_pj,pasien.umur,reg_periksa.tgl_registrasi,rujuk.tgl_rujuk,rujuk.bagian,rujuk.ku,rujuk.gcs,rujuk.respirasi,penjab.png_jawab from reg_periksa "+
                "inner join pasien inner join rujuk inner join dokter inner join penjab  on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rawat=rujuk.no_rawat and rujuk.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSuratRujukanActionPerformed

    private void ketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ketKeyPressed
        Valid.pindah(evt,TTmpRujuk,TDiagnosa);
    }//GEN-LAST:event_ketKeyPressed

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt,DTPRujuk,CmbMenit);
    }//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt,CmbJam,CmbDetik);
    }//GEN-LAST:event_CmbMenitKeyPressed

    private void ambulanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ambulanceKeyPressed
        Valid.pindah(evt,KdDok,BtnSimpan);
    }//GEN-LAST:event_ambulanceKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt,CmbMenit,TTmpRujuk);
    }//GEN-LAST:event_CmbDetikKeyPressed

    private void ktrujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ktrujukKeyPressed
        Valid.pindah(evt,TDiagnosa,KdDok);
    }//GEN-LAST:event_ktrujukKeyPressed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void BagianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BagianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BagianKeyPressed

    private void btnTerapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerapiActionPerformed
       if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            cariobat.setNoRawat(TNoRw.getText());
            cariobat.tampil();
            cariobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobat.setLocationRelativeTo(internalFrame1);
            cariobat.setVisible(true);
        }
    }//GEN-LAST:event_btnTerapiActionPerformed

    private void btnPemeriksaanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPemeriksaanPjActionPerformed
    if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            cariradiologi.setNoRawat(TNoRw.getText());
            cariradiologi.tampil();
            cariradiologi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariradiologi.setLocationRelativeTo(internalFrame1);
            cariradiologi.setVisible(true);
        }
    }//GEN-LAST:event_btnPemeriksaanPjActionPerformed

    private void btnPemeriksaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPemeriksaanLabActionPerformed
         if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            carilaborat.setNoRawat(TNoRw.getText());
            carilaborat.tampil();
            carilaborat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carilaborat.setLocationRelativeTo(internalFrame1);
            carilaborat.setVisible(true);
        }
    }//GEN-LAST:event_btnPemeriksaanLabActionPerformed

    private void MnSuratRujukan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratRujukan2ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            diagnosa="";
            keluar="";
            try {
                psobat=koneksi.prepareStatement("select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                       "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.nama_brng ");            
                
                try{   
                    psobat.setString(1,TNoRw.getText());
                    rs=psobat.executeQuery();
                    while(rs.next()){
                        if(diagnosa.equals("")){
                            diagnosa=rs.getString(1);
                        }else {
                            diagnosa=diagnosa+", "+rs.getString(1);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }

            diagnosa2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="rawat inap";
                }else {
                    diagnosa2=diagnosa2+", rawat inap";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan laboratorium";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan laboratorium";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan radiologi";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan radiologi";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="operasi";
                }else {
                    diagnosa2=diagnosa2+", operasi";
                }
            }

            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());

            param.put("html","Demikianlah riwayat perawatan selama di "+akses.getnamars()+" dengan diagnosa akhir "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+". "+
                "Atas kerjasamanya kami ucapkan terima kasih");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("diagnosa",tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            param.put("tindakan",diagnosa2);
            param.put("terpi",diagnosa);
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSuratRujukanDota.jasper","report","::[ Surat Rujukan ]::",
                "select rujuk.kat_rujuk,rujuk.keterangan_diagnosa,rujuk.alergi,rujuk.rujuk_ke,rujuk.no_rujuk,reg_periksa.no_rawat,pasien.alamat,dokter.nm_dokter,rujuk.suhu,rujuk.tensi,rujuk.nadi,rujuk.spo2,rujuk.pemeriksaan, "+
                "reg_periksa.no_rkm_medis,pasien.jk,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.nm_pasien,rujuk.pemeriksaanpj,rujuk.pemeriksaanlab,rujuk.terapi,"+
                "reg_periksa.almt_pj,pasien.umur,reg_periksa.tgl_registrasi,rujuk.tgl_rujuk,rujuk.bagian,rujuk.ku,rujuk.gcs,rujuk.respirasi,rujuk.ambulance,rujuk.keterangan,rujuk.jam,rujuk.pendamping,rujuk.namapendamping,rujuk.nokendaraan,rujuk.alasanrujuk,penjab.png_jawab from reg_periksa "+
                "inner join pasien inner join rujuk inner join dokter inner join penjab  on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rawat=rujuk.no_rawat and rujuk.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }

    }//GEN-LAST:event_MnSuratRujukan2ActionPerformed

    private void NoKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoKendaraanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoKendaraanActionPerformed

    private void NoKendaraanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKendaraanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoKendaraanKeyPressed

    private void alasanrujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alasanrujukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alasanrujukActionPerformed

    private void alasanrujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alasanrujukKeyPressed
        Valid.pindah(evt,KdDok,BtnSimpan);
    }//GEN-LAST:event_alasanrujukKeyPressed

    private void pendampingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendampingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pendampingActionPerformed

    private void pendampingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pendampingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pendampingKeyPressed

    private void namapendampingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namapendampingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namapendampingActionPerformed

    private void namapendampingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namapendampingKeyPressed
        Valid.pindah(evt,TTmpRujuk,TDiagnosa);
    }//GEN-LAST:event_namapendampingKeyPressed

    private void MnSuratRujukan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratRujukan3ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            diagnosa="";
            keluar="";
            try {
                psobat=koneksi.prepareStatement("select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                       "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.nama_brng ");            
                
                try{   
                    psobat.setString(1,TNoRw.getText());
                    rs=psobat.executeQuery();
                    while(rs.next()){
                        if(diagnosa.equals("")){
                            diagnosa=rs.getString(1);
                        }else {
                            diagnosa=diagnosa+", "+rs.getString(1);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }

            diagnosa2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="rawat inap";
                }else {
                    diagnosa2=diagnosa2+", rawat inap";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan laboratorium";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan laboratorium";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan radiologi";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan radiologi";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="operasi";
                }else {
                    diagnosa2=diagnosa2+", operasi";
                }
            }

            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());

            param.put("html","Demikianlah riwayat perawatan selama di "+akses.getnamars()+" dengan diagnosa akhir "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+". "+
                "Atas kerjasamanya kami ucapkan terima kasih");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("diagnosa",tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            param.put("tindakan",diagnosa2);
            param.put("terpi",diagnosa);
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSuratRujukan2.jasper","report","::[ Surat Rujukan ]::",
                "select rujuk.keterangan_diagnosa,rujuk.alergi,rujuk.rujuk_ke,rujuk.no_rujuk,reg_periksa.no_rawat,pasien.alamat,dokter.nm_dokter,rujuk.suhu,rujuk.tensi,rujuk.nadi,rujuk.spo2,rujuk.pemeriksaan, "+
                "rujuk.keterangan,pasien.no_peserta,reg_periksa.no_rkm_medis,pasien.jk,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.nm_pasien,rujuk.pemeriksaanpj,rujuk.pemeriksaanlab,rujuk.terapi,"+
                "reg_periksa.almt_pj,pasien.umur,reg_periksa.tgl_registrasi,rujuk.tgl_rujuk,rujuk.bagian,rujuk.ku,rujuk.gcs,rujuk.respirasi,penjab.png_jawab from reg_periksa "+
                "inner join pasien inner join rujuk inner join dokter inner join penjab  on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rawat=rujuk.no_rawat and rujuk.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSuratRujukan3ActionPerformed

    private void MnSuratRujukan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratRujukan4ActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            diagnosa="";
            keluar="";
            try {
                psobat=koneksi.prepareStatement("select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                       "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.nama_brng ");            
                
                try{   
                    psobat.setString(1,TNoRw.getText());
                    rs=psobat.executeQuery();
                    while(rs.next()){
                        if(diagnosa.equals("")){
                            diagnosa=rs.getString(1);
                        }else {
                            diagnosa=diagnosa+", "+rs.getString(1);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }

            diagnosa2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="rawat inap";
                }else {
                    diagnosa2=diagnosa2+", rawat inap";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan laboratorium";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan laboratorium";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan radiologi";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan radiologi";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="operasi";
                }else {
                    diagnosa2=diagnosa2+", operasi";
                }
            }

            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());

            param.put("html","Demikianlah riwayat perawatan selama di "+akses.getnamars()+" dengan diagnosa akhir "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+". "+
                "Atas kerjasamanya kami ucapkan terima kasih");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("diagnosa",tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            param.put("tindakan",diagnosa2);
            param.put("terpi",diagnosa);
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSuratRujukanNeo.jasper","report","::[ Surat Rujukan ]::",
                "select rujuk.kat_rujuk,rujuk.keterangan_diagnosa,rujuk.alergi,rujuk.rujuk_ke,rujuk.no_rujuk,reg_periksa.no_rawat,pasien.alamat,dokter.nm_dokter,rujuk.suhu,rujuk.tensi,rujuk.nadi,rujuk.spo2,rujuk.pemeriksaan, "+
                "reg_periksa.no_rkm_medis,pasien.jk,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.nm_pasien,rujuk.pemeriksaanpj,rujuk.pemeriksaanlab,rujuk.terapi,rujuk.lainnya,rujuk.tindakan,"+
                "reg_periksa.almt_pj,pasien.umur,reg_periksa.tgl_registrasi,rujuk.tgl_rujuk,rujuk.bagian,rujuk.ku,rujuk.gcs,rujuk.respirasi,rujuk.ambulance,rujuk.keterangan,rujuk.jam,rujuk.pendamping,rujuk.namapendamping,rujuk.nokendaraan,rujuk.alasanrujuk,penjab.png_jawab from reg_periksa "+
                "inner join pasien inner join rujuk inner join dokter inner join penjab  on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rawat=rujuk.no_rawat and rujuk.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);
            
            Valid.MyReportqry("rptSuratRujukanNeo2.jasper","report","::[ Surat Rujukan ]::",
                "select rujuk.kat_rujuk,rujuk.keterangan_diagnosa,rujuk.alergi,rujuk.rujuk_ke,rujuk.no_rujuk,reg_periksa.no_rawat,pasien.alamat,dokter.nm_dokter,rujuk.suhu,rujuk.tensi,rujuk.nadi,rujuk.spo2,rujuk.pemeriksaan, "+
                "reg_periksa.no_rkm_medis,pasien.jk,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.nm_pasien,rujuk.pemeriksaanpj,rujuk.pemeriksaanlab,rujuk.terapi,rujuk.lainnya,"+
                "reg_periksa.almt_pj,pasien.umur,reg_periksa.tgl_registrasi,rujuk.tgl_rujuk,rujuk.bagian,rujuk.ku,rujuk.gcs,rujuk.respirasi,rujuk.ambulance,rujuk.keterangan,rujuk.jam,rujuk.pendamping,rujuk.namapendamping,rujuk.nokendaraan,rujuk.alasanrujuk,penjab.png_jawab from reg_periksa "+
                "inner join pasien inner join rujuk inner join dokter inner join penjab  on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rawat=rujuk.no_rawat and rujuk.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);
                this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSuratRujukan4ActionPerformed

    private void btnTerapi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerapi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTerapi1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRujuk dialog = new DlgRujuk(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Alergi;
    private widget.TextBox Bagian;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPRujuk;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Gcs;
    private widget.TextBox KdDok;
    private widget.TextBox Ku;
    private widget.Label LCount;
    private widget.TextArea Lainnya;
    private javax.swing.JMenuItem MnSuratRujukan;
    private javax.swing.JMenuItem MnSuratRujukan2;
    private javax.swing.JMenuItem MnSuratRujukan3;
    private javax.swing.JMenuItem MnSuratRujukan4;
    private widget.TextBox Nadi;
    private widget.TextBox NoKendaraan;
    private javax.swing.JPanel PanelInput;
    private widget.TextArea Pemeriksaan;
    private widget.TextArea PemeriksaanLab;
    private widget.TextArea PemeriksaanPj;
    private widget.TextBox Rr;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox Spo;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TDiagnosa;
    private widget.TextBox TDokter;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRj;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TTmpRujuk;
    private widget.TextBox Td;
    private widget.TextArea Terapi;
    private widget.TextArea Tindakan;
    private widget.ComboBox alasanrujuk;
    private widget.ComboBox ambulance;
    private widget.Button btnDokter;
    private widget.Button btnPemeriksaanLab;
    private widget.Button btnPemeriksaanPj;
    private widget.Button btnTerapi;
    private widget.Button btnTerapi1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox ket;
    private widget.ComboBox ktrujuk;
    private widget.TextBox namapendamping;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ComboBox pendamping;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables


    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            tgl=" rujuk.tgl_rujuk between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
             sql="select rujuk.no_rujuk,rujuk.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "rujuk.tgl_rujuk,rujuk.jam,rujuk.rujuk_ke,rujuk.bagian,rujuk.kat_rujuk,rujuk.ambulance,rujuk.keterangan,rujuk.nokendaraan,"+
                "rujuk.alasanrujuk,rujuk.pendamping,rujuk.namapendamping,rujuk.ku,rujuk.gcs,rujuk.respirasi,rujuk.suhu,rujuk.tensi,rujuk.nadi,rujuk.spo2,rujuk.alergi,"+
                "rujuk.pemeriksaan,rujuk.pemeriksaanpj,rujuk.pemeriksaanlab,rujuk.terapi,rujuk.lainnya,rujuk.tindakan,rujuk.keterangan_diagnosa,rujuk.kd_dokter,dokter.nm_dokter "+
                "from rujuk inner join reg_periksa inner join pasien inner join dokter "+
                "on rujuk.no_rawat=reg_periksa.no_rawat "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and rujuk.kd_dokter=dokter.kd_dokter "+
                "where "+tgl+"and no_rujuk like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.rujuk_ke like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.tgl_rujuk like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.keterangan_diagnosa like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and rujuk.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                " order by rujuk.no_rujuk";
            ps=koneksi.prepareStatement(sql);
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    String[] data={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                                   rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                                   rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                                   rs.getString(13),rs.getString(14),rs.getString(15),rs.getString(16),
                                   rs.getString(17),rs.getString(18),rs.getString(19),rs.getString(20),
                                   rs.getString(21),rs.getString(22),rs.getString(23),rs.getString(24),
                                   rs.getString(25),rs.getString(26),rs.getString(27),rs.getString(28),
                                   rs.getString(29),rs.getString(30),rs.getString(31),rs.getString(32)};
                        tabMode.addRow(data);
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgRujuk.tampil() : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        TNoRj.setText("");
        TTmpRujuk.setText("");
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        KdDok.setText("");
        TDokter.setText("");
        TDiagnosa.setText("");
        DTPRujuk.setDate(new Date());
        Valid.autoNomer("rujuk","R",9,TNoRj);
        TNoRj.requestFocus();
        ktrujuk.setSelectedIndex(0);
        ambulance.setSelectedIndex(0);
        ket.setText("");
        NoKendaraan.setText("");
        alasanrujuk.setSelectedIndex(0);
        pendamping.setSelectedIndex(0);
        namapendamping.setText("");
       
    }

    public void load(String param) {
        if(! param.equals("")){
            KdDok.setText(param);   
            KdDok.setEditable(false);
            btnDokter.setEnabled(false);
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+param+"'",TDokter);
        }else if(param.equals("")){
            KdDok.setText("");   
            KdDok.setEditable(true);
            btnDokter.setEnabled(true);
        }
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(DTPRujuk,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            CmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(0,2));
            CmbMenit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(3,5));
            CmbDetik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(6,8));
            TTmpRujuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Bagian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            ktrujuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            ambulance.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            ket.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NoKendaraan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            alasanrujuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            pendamping.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            namapendamping.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Ku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            Gcs.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Rr.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Td.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Spo.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Pemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            PemeriksaanPj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            PemeriksaanLab.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Lainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            TDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KdDok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            TDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            
            
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();              
        KdDok.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='"+norwt+"'"));
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+KdDok.getText()+"'",TDokter);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,500));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getrujukan_keluar());
        BtnHapus.setEnabled(akses.getrujukan_keluar());
        BtnPrint.setEnabled(akses.getrujukan_keluar());
    }

   private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
}
