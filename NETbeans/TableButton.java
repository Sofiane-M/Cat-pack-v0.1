 private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
     int nbrE = Integer.parseInt(jLabel1.getText());
     int nbrM = Integer.parseInt(jLabel2.getText());
     
     Object[] col = new Object[nbrM + 1];
     col[0] = "";
            for(int i = 1; i < nbrM; i++){
                col[i] = "Module" + i;
            }
            
           Object[][] perf = new Object[nbrE][nbrM + 1 ];
           for(int j = 0 ; j < nbrE;j++){
               perf[j][0]= "Etudiant"+(j + 1);
           }
           
           jTable1.setModel(new JTable(perf,col).getModel());
    } 
