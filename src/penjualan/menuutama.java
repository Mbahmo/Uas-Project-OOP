/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjualan;

import java.awt.Dimension;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author anthonylee
 */
public class menuutama extends javax.swing.JFrame {
    
    public String iduser, username;
    private DefaultTableModel model, model2, model3;
    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;
    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * Creates new form menuutama
     */
    public menuutama() {
        initComponents();
        
        model = new  DefaultTableModel()
        { @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        jTable1.setModel(model);
        model.addColumn("Id User");
        model.addColumn("Nama Pengguna");
        model.addColumn("Username");
        model.addColumn("Alamat");
        model.addColumn("No Telp");
        
        model2 = new  DefaultTableModel()
        { @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        jTable2.setModel(model2);
        model2.addColumn("Id Barang");
        model2.addColumn("Nama Barang");
        model2.addColumn("Harga");
        
        model3 = new  DefaultTableModel()
        { @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        jTable3.setModel(model3);
        model3.addColumn("ID");
        model3.addColumn("ID & Nama Barang");
        model3.addColumn("Jumlah");
        model3.addColumn("Total");
        model3.addColumn("Admin");
        
        tampildatauser();
        tampildatabarang();
        tampildatapenjualan();
    }
    void tampildatauser() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Statement statement=(Statement)
                    koneksi.getConnection().createStatement();
         String sql="SELECT * FROM tbuser";
            ResultSet r = statement.executeQuery(sql);

           while (r.next()) {
                Object[] o=new Object[5];
                o[0] = r.getString("IdUser");
                o[1] = r.getString("NamaUser");
                o[2] = r.getString("Username");
                o[3] = r.getString("AlamatUser");
                o[4] = r.getString("NoTelpUser");
                model.addRow(o);
           }
           r.close();
           statement.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
    void tampildatabarang() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try {
            Statement statement=(Statement)
                    koneksi.getConnection().createStatement();
         String sql="SELECT * FROM tbbarang";
            ResultSet r = statement.executeQuery(sql);

           while (r.next()) {
                Object[] o=new Object[5];
                o[0] = r.getString("IdBarang");
                o[1] = r.getString("NamaBarang");
                o[2] = r.getString("HargaBarang");
                model2.addRow(o);
           }
           r.close();
           statement.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
    void tampildatapenjualan() {
        model3.getDataVector().removeAllElements();
        model3.fireTableDataChanged();
        try {
            Statement statement = (Statement)
            koneksi.getConnection().createStatement();
            String sql          = "SELECT * FROM tbdetailpenjualan INNER JOIN tbpenjualan on tbpenjualan.idpenjualan = tbdetailpenjualan.idpenjualan "
                 + "INNER JOIN tbbarang on tbbarang.idbarang = tbdetailpenjualan.idbarang INNER JOIN tbuser ON tbpenjualan.iduser = tbuser.iduser where tbpenjualan.statuspenjualan = 'FALSE'";
            ResultSet r = statement.executeQuery(sql);

           while (r.next()) {
                Object[] o=new Object[5];
                o[0] = r.getString("IdDetailPenjualan");
                o[1] = r.getString("IdBarang") + "     -     "+ r.getString("NamaBarang");
                o[2] = r.getString("JumlahBarang");
                o[3] = r.getDouble("HargaBarang") * r.getInt("JumlahBarang");
                o[4] = r.getString("NamaUser");
                model3.addRow(o);
           }
           r.close();
           statement.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
    
    void tableusersearch(){
       DefaultTableModel tabelTampil1 = new DefaultTableModel();
       tabelTampil1.addColumn("Id User");
       tabelTampil1.addColumn("Nama Pengguna");
       tabelTampil1.addColumn("Username");
       tabelTampil1.addColumn("Alamat");
       tabelTampil1.addColumn("No Telp");
       
       try {
            Statement statement=(Statement)
            koneksi.getConnection().createStatement();
            String search = jTextField1.getText();
            String sql = "Select * from tbuser where IdUser like '%" + search + "%'" 
                    + "or NamaUser like '%"   + search + "%'"
                    + "or Username like '%"   + search + "%'"
                    + "or AlamatUser like '%" + search + "%'"
                    + "or NoTelpUser like '%" + search + "%'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
            tabelTampil1.addRow(new Object[]{
                rs.getString("IdUSer"),
                rs.getString("NamaUser"),
                rs.getString("Username"),
                rs.getString("AlamatUser"),
                rs.getString("NoTelpUser"),
            });
            }
          jTable1.setModel(tabelTampil1);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    void tablebarangsearch(){
       DefaultTableModel tabelTampil2 = new DefaultTableModel();
       tabelTampil2.addColumn("Id Barang");
       tabelTampil2.addColumn("Nama Barang");
       tabelTampil2.addColumn("Harga");
       
       try {
            Statement statement=(Statement)
            koneksi.getConnection().createStatement();
            String search = jTextField2.getText();
            String sql = "Select * from tbbarang where IdBarang like '%" + search + "%'" 
                    + "or NamaBarang like '%"   + search + "%'"
                    + "or HargaBarang like '%" + search + "%'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
            tabelTampil2.addRow(new Object[]{
                rs.getString("IdBarang"),
                rs.getString("NamaBarang"),
                rs.getString("HargaBarang"),
            });
            }
          jTable2.setModel(tabelTampil2);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        User = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        Barang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        Penjualan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Utama");
        setMinimumSize(new java.awt.Dimension(800, 500));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(64, 134, 244));

        jTabbedPane1.setBackground(new java.awt.Color(245, 245, 245));

        User.setBackground(new java.awt.Color(240, 240, 240));
        User.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 3, true));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(10, 10, 10));
        jLabel1.setText("Search :");

        jPanel7.setBackground(new java.awt.Color(64, 134, 244));

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 25)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Master User");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jTextField1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tambah.png"))); // NOI18N
        jButton1.setText("   Tambah");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        jButton2.setText("  Update");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        jButton3.setText("  Delete");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton4.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/key.png"))); // NOI18N
        jButton4.setText("  Ganti Password");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UserLayout = new javax.swing.GroupLayout(User);
        User.setLayout(UserLayout);
        UserLayout.setHorizontalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(UserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(UserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        UserLayout.setVerticalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("User", User);

        Barang.setBackground(new java.awt.Color(240, 240, 240));
        Barang.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 3, true));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(10, 10, 10));
        jLabel2.setText("Search :");

        jPanel8.setBackground(new java.awt.Color(64, 134, 244));

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 25)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Master Barang");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jTextField2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tambah.png"))); // NOI18N
        jButton5.setText("   Tambah");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        jButton6.setText("  Update");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        jButton7.setText("  Delete");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton8.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/report.png"))); // NOI18N
        jButton8.setText(" Cetak");
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BarangLayout = new javax.swing.GroupLayout(Barang);
        Barang.setLayout(BarangLayout);
        BarangLayout.setHorizontalGroup(
            BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BarangLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(BarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(BarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        BarangLayout.setVerticalGroup(
            BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarangLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Barang", Barang);

        Penjualan.setBackground(new java.awt.Color(240, 240, 240));
        Penjualan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 3, true));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(10, 10, 10));
        jLabel3.setText("Search :");

        jPanel9.setBackground(new java.awt.Color(64, 134, 244));

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 25)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Penjualan");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(10, 10, 10))
        );

        jTextField3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tambah.png"))); // NOI18N
        jButton9.setText("   Tambah");
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        jButton10.setText("  Update");
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        jButton11.setText("  Delete");
        jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton12.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/payment.png"))); // NOI18N
        jButton12.setText(" Bayar");
        jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/report.png"))); // NOI18N
        jButton13.setText(" Cetak");
        jButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PenjualanLayout = new javax.swing.GroupLayout(Penjualan);
        Penjualan.setLayout(PenjualanLayout);
        PenjualanLayout.setHorizontalGroup(
            PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PenjualanLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        PenjualanLayout.setVerticalGroup(
            PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PenjualanLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Penjualan", Penjualan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jMenu1.setText("Menu");

        jMenuItem1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/exit.png"))); // NOI18N
        jMenuItem1.setText("Logout");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logout.png"))); // NOI18N
        jMenuItem2.setText("Keluar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        login login = new login();
        int jawaban = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?",
            "Konfirmasi Logout",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE
        );
        if (jawaban == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "     Sampai Jumpa " + username);
            login.show();
            this.dispose();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        tableusersearch();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambahdatauser tambahdatauser = new tambahdatauser(this, rootPaneCheckingEnabled);
        tambahdatauser.show();
        tambahdatauser.setAlwaysOnTop(true);
        jTable1.setModel(model);
        tampildatauser();
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:  int i=jTable1.getSelectedRow();
        int i = jTable1.getSelectedRow();
        if (i == -1){
            JOptionPane.showMessageDialog(this, "   Silahkan Pilih Data Pegawai");
        }
        else {
            String iduser       = (String) jTable1.getValueAt(i, 0);
            String nama         = (String) jTable1.getValueAt(i, 1);
            String username     = (String) jTable1.getValueAt(i, 2);
            String alamat       = (String) jTable1.getValueAt(i, 3);
            String notelp       = (String) jTable1.getValueAt(i, 4);
            
            updatedatauser updatedatauser    = new updatedatauser(this, rootPaneCheckingEnabled);
            updatedatauser.iduser            = iduser;
            updatedatauser.jTextField1.setText(username);   
            updatedatauser.jTextField2.setText(nama);  
            updatedatauser.jTextField3.setText(notelp);  
            updatedatauser.jTextArea1.setText(alamat);  
            
            updatedatauser.show();
            updatedatauser.setAlwaysOnTop(true);
            jTable1.setModel(model);
            tampildatauser();
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int i = jTable1.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "      Belum Memilih Data");
            return;
        }
        else {
            try {

                String iduser     = (String) jTable1.getValueAt(i, 0);
                Statement statement  = (Statement)
                koneksi.getConnection().createStatement();

                int jawaban = JOptionPane.showConfirmDialog(this, "Apakah anda yakin?",
                    "Pertanyaan",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                
                if (jawaban == JOptionPane.YES_OPTION) {
                    statement.executeUpdate("delete from tbuser where IdUser=('"+iduser+"');");
                    statement.close();
                }
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                tampildatauser();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data Gagal Dihapus\n"+e.getMessage());
            }
        }
        jTable1.setModel(model);
        tampildatauser();
        jTextField1.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JLabel jPassword     = new JLabel("Password Lama");
        JTextField password  = new JPasswordField()
        {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 30);
            }
        };
        JLabel jPassword2      = new JLabel("Password Baru");
        JTextField password2   = new JPasswordField()
        {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 30);
            }
        };
        int i=jTable1.getSelectedRow();

        if (i==-1){
            JOptionPane.showMessageDialog(this, "Silahkan  pilih user terlebih dahulu");
        }
        else {
            Object[] ob  = {jPassword, password};
            Object[] ob2 = {jPassword2, password2};

            int result  = JOptionPane.showConfirmDialog(null, ob, "Masukkan Password Lama",  JOptionPane.OK_CANCEL_OPTION);
            int result2 = JOptionPane.showConfirmDialog(null, ob2, "Masukkan Password Baru", JOptionPane.OK_CANCEL_OPTION);
            String passwordValue  = password.getText();
            String passwordValue2 = password2.getText();

            if (passwordValue.equals("")) {
                JOptionPane.showMessageDialog(this, "Password Lama Masih Kosong");
            }
            else if(passwordValue2.equals("")) {
                JOptionPane.showMessageDialog(this, "Password Baru Masih Kosong");
            }
            else {
                String id = (String) model.getValueAt(i, 0);
                try {
                    Statement statement=(Statement)
                    koneksi.getConnection().createStatement();
                    String sql ="SELECT `tbuser`.`Password` FROM `tbuser` Where `Iduser`='"+id+"' AND `Password` = MD5('"+passwordValue+"')";
                    ResultSet r = statement.executeQuery(sql);

                    if (!r.next()) {
                        JOptionPane.showMessageDialog(this, "Password Lama Salah. Coba Lagi", "Password Salah",JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        try {
                            koneksi.getConnection().createStatement();
                            statement.executeUpdate("UPDATE `tbuser` SET `Password` = MD5('"+passwordValue2+"') WHERE `tbuser`.`IdUser` = '"+id+"';");
                            statement.close();
                            JOptionPane.showMessageDialog(this, "Berhasil Mengubah Password");
                            tampildatauser();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Gagal Mengubah Password\n"+e.getMessage());

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }

            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        tablebarangsearch();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        tambahdatabarang tambahdatabarang = new tambahdatabarang(this, rootPaneCheckingEnabled);
        tambahdatabarang.show();
        tambahdatabarang.setAlwaysOnTop(true);
        jTable2.setModel(model2);
        tampildatabarang();
        jTextField2.setText("");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int i = jTable2.getSelectedRow();
        if (i == -1){
            JOptionPane.showMessageDialog(this, "   Silahkan Pilih Data Barang");
        }
        else {
            String iduser       = (String) jTable2.getValueAt(i, 0);
            String nama         = (String) jTable2.getValueAt(i, 1);
            String harga        = (String) jTable2.getValueAt(i, 2);
            
            updatedatabarang updatedatabarang  = new updatedatabarang(this, rootPaneCheckingEnabled);
            updatedatabarang.idbarang          = iduser;
            updatedatabarang.jTextField1.setText(nama);
            updatedatabarang.jTextField2.setText(harga);
            
            updatedatabarang.show();
            updatedatabarang.setAlwaysOnTop(true);
            jTable2.setModel(model2);
            tampildatabarang();
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int i = jTable2.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "      Belum Memilih Data");
            return;
        }
        else {
            try {

                String idbarang     = (String) jTable2.getValueAt(i, 0);
                Statement statement  = (Statement)
                koneksi.getConnection().createStatement();

                int jawaban = JOptionPane.showConfirmDialog(this, "Apakah anda yakin?",
                    "Pertanyaan",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                
                if (jawaban == JOptionPane.YES_OPTION) {
                    statement.executeUpdate("delete from tbbarang where IdBarang=('"+idbarang+"');");
                    statement.close();
                }
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                tampildatauser();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data Gagal Dihapus\n"+e.getMessage());
            }
        }
        jTable2.setModel(model);
        tampildatabarang();
        jTextField2.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        try {
            InputStream file = getClass().getResourceAsStream("/report/barang.jrxml");
            jasperDesign = JRXmlLoader.load(file);
            param.clear();
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint  = JasperFillManager.fillReport(jasperReport, param, koneksi.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        tambahdatapenjualan tambahdatapenjualan = new tambahdatapenjualan(this, rootPaneCheckingEnabled);
        tambahdatapenjualan.iduser = iduser;
        tambahdatapenjualan.show();
        tambahdatapenjualan.setAlwaysOnTop(true);
        jTable3.setModel(model3);
        tampildatapenjualan();
        jTextField3.setText("");
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        int i = jTable3.getSelectedRow();
        if (i == -1){
            JOptionPane.showMessageDialog(this, "   Silahkan Pilih Data Barang");
        }
        else {
            String iddetailpenjualanpenjualan  = (String) jTable3.getValueAt(i, 0);
            String barang                      = (String) jTable3.getValueAt(i, 1);
            String jumlah                      = (String) jTable3.getValueAt(i, 2);
                
            updatedatapenjualan updatedatapenjualan  = new updatedatapenjualan(this, rootPaneCheckingEnabled);
            updatedatapenjualan.iddetailpenjualan    = iddetailpenjualanpenjualan;
            updatedatapenjualan.jComboBox1.setSelectedItem(barang);
            updatedatapenjualan.jTextField2.setText(jumlah);
            updatedatapenjualan.show();
            updatedatapenjualan.setAlwaysOnTop(true);
            jTable3.setModel(model3);
            tampildatapenjualan();
            jTextField3.setText("");
        }
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        int i = jTable3.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "      Belum Memilih Data");
            return;
        }
        else {
            try {

                String iddetailpenjualan     = (String) jTable3.getValueAt(i, 0);
                Statement statement  = (Statement)
                koneksi.getConnection().createStatement();

                int jawaban = JOptionPane.showConfirmDialog(this, "Apakah anda yakin?",
                    "Pertanyaan",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                
                if (jawaban == JOptionPane.YES_OPTION) {
                    statement.executeUpdate("delete from tbdetailpenjualan where IdDetailPenjualan=('"+iddetailpenjualan+"');");
                    statement.close();
                }
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                tampildatauser();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data Gagal Dihapus\n"+e.getMessage());
            }
        }
        jTable3.setModel(model3);
        tampildatapenjualan();
        jTextField3.setText("");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        pembayaran pembayaran = new pembayaran(this, rootPaneCheckingEnabled);
        pembayaran.show();
        pembayaran.setAlwaysOnTop(true);
        jTable3.setModel(model3);
        tampildatapenjualan();
        jTextField3.setText("");
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        try {
            InputStream file = getClass().getResourceAsStream("/report/penjualan.jrxml");
            jasperDesign = JRXmlLoader.load(file);
            param.clear();
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint  = JasperFillManager.fillReport(jasperReport, param, koneksi.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        int jawaban = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar aplikasi?",
            "Konfirmasi Keluar",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE
        );
        if (jawaban == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "     Sampai Jumpa " + username);
            this.dispose();
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menuutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menuutama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Barang;
    private javax.swing.JPanel Penjualan;
    private javax.swing.JPanel User;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable1;
    public javax.swing.JTable jTable2;
    public javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
