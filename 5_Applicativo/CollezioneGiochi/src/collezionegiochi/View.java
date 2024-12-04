/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package collezionegiochi;
import static collezionegiochi.Config.API_KEY;
import static collezionegiochi.Config.immagineDefault;
import static collezionegiochi.Model.httpRequest;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashSet;
/**
 *
 * @author sidney.canonica
 */
public class View extends javax.swing.JFrame {

    //Creo i componenti per il popup
    /*private JLabel lblPopup = new JLabel("Nome lista:");
    private JTextField tfPopup = new JTextField();
    private JButton btnPopup = new JButton("Conferma");*/
    
    //Componenti per le copertine dei giochi
    private ArrayList<String> paths =new ArrayList<>();
    private ArrayList<Image> resizeImage = new ArrayList<>();
    private ArrayList<Game> arGiochi = new ArrayList<>();
    public Game getArGioco(int i){
        return arGiochi.get(i);
    }
    public Image getResizeImage(int i){
        return resizeImage.get(i);
    }

    private Image image = null;
    private ArrayList<String> titoli= new ArrayList<>();
    public String getTitolo(int i){
        return titoli.get(i);
    }
    private String rispostaAPI;

    public String getRispostaAPI() {
        return rispostaAPI;
    }
    private View CurrentView;
    public void setCurrentView(View v){
        this.CurrentView = v;
    }
    public View getCurrentView(){
        return this.CurrentView;
    }
    
    
    MouseListener SchedaMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object source = e.getSource();
                if (source instanceof JLabel) {
                    JLabel clickedLabel = (JLabel) source;
                    int i=Integer.parseInt( clickedLabel.getName());
                    try {           
                        new DialogSchedaGioco(new javax.swing.JFrame(),true,i,getCurrentView()).setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

    
    public void DisponiImmagini(String MobyURL){
            paths.clear();
            paths.clear();
            resizeImage.clear();
            arGiochi.clear();
            
            try {
                //Dichiaro variabili
            rispostaAPI="";
            Model m = new Model();
            SocketAddress addr = new InetSocketAddress("localhost", 5865);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
            javax.swing.ImageIcon imageFile = null;
            JLabel GiocoLabel = null;
            JLabel ErrorLabel = null;
            URL url = null;
            URLConnection conn= null;
            int contaNull=-1;
            
            
            rispostaAPI = Model.httpRequest(MobyURL);
            
            arGiochi=m.PrendiGiochi(rispostaAPI);
            for(Game g : arGiochi){
                if(g.sample_cover == null){
                    GiocoLabel = new JLabel();
                    iconsPanel.add(GiocoLabel);
                    try { 
                        Image imageDefault = ImageIO.read(immagineDefault);
                        GiocoLabel.setIcon(new javax.swing.ImageIcon(imageDefault));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    GiocoLabel.setName(contaNull+"");
                    contaNull--;
                    GiocoLabel.addMouseListener(SchedaMouseListener);
                }else{
                    paths.add(g.sample_cover.image);
                }
                titoli.add(g.title);
            }

            if(arGiochi.isEmpty()){
                ErrorLabel = new JLabel();
                iconsPanel.add(ErrorLabel);
                ErrorLabel.setText("La tua ricerca non ha dato risultati.");
            }
            
            for(int i=0;i<paths.size();i++){
                url = new URL(paths.get(i));
                conn = url.openConnection(proxy);
                GiocoLabel = new JLabel();

                iconsPanel.add(GiocoLabel);
                image = ImageIO.read(conn.getInputStream());
                resizeImage.add(image.getScaledInstance(125, 150, Image.SCALE_DEFAULT));
                imageFile= new javax.swing.ImageIcon(resizeImage.get(i));
                image.flush();

                GiocoLabel.setName(i+"");
                GiocoLabel.setIcon(imageFile);
                GiocoLabel.addMouseListener(SchedaMouseListener);
            }
        } catch (Exception ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CreaLista(){
        String query="";
        if(tfLista.getText().isEmpty() || tfLista.getText().isBlank()){

        }else{
            query ="INSERT INTO lista(nome) VALUES('"+tfLista.getText()+"')";
            Model.DBInsert(query);
        }
    }
    
    MouseListener ListaMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object source = e.getSource();
                if (source instanceof JLabel) {
                    JLabel clickedLabel = (JLabel) source;
                    String query="SELECT * FROM contiene WHERE id="+clickedLabel.getName();
                    //SELECT * FROM contiene WHERE id=idLista
                    /*var rs=Model.DBQuery(query);
                    while(rs.next()){
                        
                    }*/
                    
                }
            }
        };
    public void MostraGiochiLista(String listId){
        String query="SELECT * FROM contiene WHERE id = "+listId;
        var rs=Model.DBQuery(query);
        try {
            while(rs.next()){
                rs.getString("gioco_titolo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private ArrayList<String> ListsNames;
    public ArrayList<String> getListsNames(){
        return ListsNames;
    }
    private ArrayList<String> ListsId;
    public ArrayList<String> getListsId(){
        return ListsId;
    }
    public void CaricaListe(){
        String query="SELECT id,nome FROM lista";
        ListsNames= new ArrayList<>();
        ListsId= new ArrayList<>();
        JLabel listLabel=null;
        var rs = Model.DBQuery(query);
        try {
            while(rs.next()){
                ListsNames.add(rs.getString("nome"));
                ListsId.add(rs.getString("id"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0;i<ListsNames.size();i++){
            listLabel= new JLabel();
            leftPanel.add(listLabel);
            listLabel.setName(ListsId.get(i));
            listLabel.setText(ListsNames.get(i));
            listLabel.addMouseListener(ListaMouseListener);
            leftPanel.updateUI();
        }    
    }
    public View() {
        initComponents();

        
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupNewList = new javax.swing.JPopupMenu();
        jFrame1 = new javax.swing.JFrame();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        iconsPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        btnAddList = new javax.swing.JButton();
        lblListe = new javax.swing.JLabel();
        tfLista = new java.awt.TextField();
        rightPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        textField1 = new java.awt.TextField();
        lblFiltri = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Collezione di Videogiochi");
        setMinimumSize(new java.awt.Dimension(1000, 500));
        setSize(new java.awt.Dimension(1080, 500));

        iconsPanel.setEnabled(false);
        iconsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconsPanelMouseClicked(evt);
            }
        });
        getContentPane().add(iconsPanel, java.awt.BorderLayout.CENTER);

        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAddList.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAddList.setText("+");
        btnAddList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddListActionPerformed(evt);
            }
        });
        leftPanel.add(btnAddList);

        lblListe.setText("Liste:");
        leftPanel.add(lblListe);

        tfLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfListaActionPerformed(evt);
            }
        });
        leftPanel.add(tfLista);

        getContentPane().add(leftPanel, java.awt.BorderLayout.WEST);

        rightPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Cerca");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        textField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField1ActionPerformed(evt);
            }
        });

        lblFiltri.setText("Filtri:");

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addComponent(lblFiltri)
                        .addGap(120, 120, 120))
                    .addComponent(textField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblFiltri)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(rightPanel, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddListActionPerformed
        CreaLista();

    }//GEN-LAST:event_btnAddListActionPerformed

    private void textField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        iconsPanel.removeAll();
        iconsPanel.updateUI();
        
        if(!textField1.getText().isBlank() || !textField1.getText().isEmpty()){
            try {
                DisponiImmagini("https://api.mobygames.com/v1/games?api_key="+API_KEY+"&title="+URLEncoder.encode(textField1.getText(), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void iconsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconsPanelMouseClicked

    }//GEN-LAST:event_iconsPanelMouseClicked

    private void tfListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfListaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfListaActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                View v = new View();
                v.DisponiImmagini("https://api.mobygames.com/v1/games?api_key="+API_KEY+"&limit=20");
                v.setVisible(true);
                v.setCurrentView(v);
                v.CaricaListe();
            }
        });
    }
public javax.swing.JFrame getjFrame1(){
        return this.jFrame1;
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddList;
    private javax.swing.JPanel iconsPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblFiltri;
    private javax.swing.JLabel lblListe;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPopupMenu popupNewList;
    private javax.swing.JPanel rightPanel;
    private java.awt.TextField textField1;
    private java.awt.TextField tfLista;
    // End of variables declaration//GEN-END:variables


}
