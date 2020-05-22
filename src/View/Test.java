/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.DefaultListModel;
import Seances.Seance;
import Utilisateurs.User;
import Utilisateurs.Utilisateur;

/**
 *
 * @author Matth
 */
public class Test extends javax.swing.JFrame {

	final DefaultListModel<String> listModel = new DefaultListModel<String>();
    /**
     * Creates new form Test
     */
    public Test(User user) {
        initComponents();
        
        for (Utilisateur enseignant : user.ListeUtilisateurs().getEnseignants()) {
			System.out.println(enseignant);
			for (Seance seance : user.ListeSeances().getByUtilisateur(enseignant)) {
				listModel.addElement(seance.getID() + " - " + seance.toString());
			}
			
			System.out.println("------------\n");
		}
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setModel(listModel);
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
