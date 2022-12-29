import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.util.Objects;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class App2 {

	private JFrame frame;
    private JButton Select;
    private File file;
    private JButton back;
    private JLabel Names;
    private JLabel Title;
    private JLabel ISTLogo1;
    //private JLabel ISTLogo2;
    private JLabel AMC;
    private JButton classify;
    private JLabel nrvariables;
    private JTextArea txtrVariables;
    
    
    private JLabel error_label;
    //private JLabel DONEpic;
    
    private DataSet d;
    private Classifier classifier;
    
    private final JFileChooser fc = new JFileChooser();
    private final File path = new File(new File(".").getCanonicalPath());
    private JTextField textField;

    
    private void components(JFrame frame) {  //função que cria todos os compponentes de design
		
    	//Window 1	
    		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.getContentPane().setLayout(null);
            frame.setBounds(100,100,750,500);
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.white);
            frame.setTitle("Markov Random Field Classifier App");
            frame.setResizable(false);
            
            ISTLogo1 = new JLabel(new ImageIcon(Objects.requireNonNull(App1Learning.class.getResource("./Resources/ISTLogo.png"))));
            ISTLogo1.setBounds(261, 0, 183, 203);
            frame.getContentPane().add(ISTLogo1);
            
            Names = new JLabel("Ana Alfaiate 102903 | Catarina Freitas 102630 | Letícia Sousa 102578 | Raquel Coelho 102881");
            Names.setBounds(99, 437, 552, 16);
            Names.setFont(new Font("Tahoma", Font.PLAIN, 13));
            frame.getContentPane().add(Names);

    		Select = new JButton("Select Sample File\r\n");
    	    Select.setOpaque(false);
    	    Select.setBorder(new LineBorder(Color.white));
    	    Select.setFont(new Font("Tahoma", Font.PLAIN, 26));
    	    Select.setBounds(221, 287, 268, 51);
    	    frame.getContentPane().add(Select);
    	    
    	    Title = new JLabel("Markov Random Field Classifier App");
    	    Title.setFont(new Font("Tahoma", Font.PLAIN, 26));
    	    Title.setBounds(148, 186, 423, 36);
    	    frame.getContentPane().add(Title);
    	    
            AMC = new JLabel("AMC 2022/2023");
            AMC.setFont(new Font("Tahoma", Font.PLAIN, 13));
            AMC.setBounds(305, 225, 109, 22);
            frame.getContentPane().add(AMC);
            
            error_label = new JLabel();
            error_label.setBorder(new LineBorder(Color.white));
            error_label.setBackground(Color.white);
            error_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
            error_label.setHorizontalAlignment(SwingConstants.CENTER);
            error_label.setBounds(261, 348, 210, 20);
            error_label.setText(null);
            frame.getContentPane().add(error_label);
            error_label.setText(null);
            
            
    //Window 2
    	    
            /*
            ISTLogo2 = new JLabel(new ImageIcon(Objects.requireNonNull(App1Learning.class.getResource("./Resources/ISTLogo.png"))));
            ISTLogo2.setBounds(590, 10, 161, 141);
            frame.getContentPane().add(ISTLogo2);
            */
            
    		back = new JButton(new ImageIcon(Objects.requireNonNull(App1Learning.class.getResource("./Resources/BackButton.png"))));
    	    back.setBorder(null);
    	    back.setVisible(false);
    	    back.setBounds(10, 10, 36, 36);
    	    frame.getContentPane().add(back);
    	   
    	    classify = new JButton("Classify");
            classify.setOpaque(false);
            classify.setBorder(new LineBorder(Color.white));
            classify.setFont(new Font("Tahoma", Font.PLAIN, 20));
            classify.setBounds(90, 348, 122, 40);
            frame.getContentPane().add(classify);
            classify.setVisible(false);
            classify.setForeground(new Color(0, 0, 0));
            
            txtrVariables = new JTextArea();
            txtrVariables.setText("Variables (var1, var2, ..., var n):");
            txtrVariables.setBounds(26, 278, 285, 22);
            frame.getContentPane().add(txtrVariables);
            txtrVariables.setVisible(false);
            
            textField = new JTextField();
            textField.setBounds(26, 304, 285, 29);
            frame.getContentPane().add(textField);
            textField.setColumns(10);
            textField.setVisible(false);
            
            
            nrvariables = new JLabel("New label");
            nrvariables.setBounds(378, 282, 237, 16);
            frame.getContentPane().add(nrvariables);
            nrvariables.setVisible(false);

          
    	}
    private void change12() {
        Select.setVisible(false);
        error_label.setText("");
        back.setVisible(true);
        classify.setVisible(true);
        nrvariables.setText("Number of variables: "+(d.getDataListArraySize() -1));
        nrvariables.setVisible(true);
        txtrVariables.setVisible(true);
        textField.setVisible(true);
        
    }
	
    private void change21() {
        Select.setVisible(true);
        error_label.setText("");
        back.setVisible(false);
        classify.setVisible(false);
        

        
    }

    private int[] textftoarray(JTextField text) {
        String values = text.getText().replaceAll("\\s","");
        String[] strValues = values.split(",");
        int[] intValues = new int[strValues.length];
        for(int i = 0; i < strValues.length; i++) {
            try {
                intValues[i] = Integer.parseInt(strValues[i]);
            } catch (NumberFormatException nfe) {
                error_label.setText("Use only integers as variables >:(");
            }
        }
        return intValues;
    }
    
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App2 window = new App2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App2() throws IOException{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fc.setCurrentDirectory(path);
        frame = new JFrame();
        components(frame);

        Select.addActionListener(e -> {
            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                try {
                    classifier = Classifier.openclf(file.getName());
                    change12();
                } catch (Exception e1) {
                    error_label.setText("Could not import Classifier file");
                }
            }
        });
  
        back.addActionListener(e -> {
            int b = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back?\nThe data will be lost", "", JOptionPane.YES_NO_OPTION);
            if(b == JOptionPane.YES_OPTION){
                classifier = null;
                change21();
            }
        });

        classify.addActionListener(e -> {
            int[] x = textftoarray(textField);
            int classe = classifier.Classify(x);
            System.out.println(classe);
         // Mostrar probstotal - normalizado
//          double soma=0;
//          for (int i=0; i<frequencias.length; i++) {
//          	soma+= probstotal[i];
//          }
//          double [] probfinal = new double [frequencias.length];
//          for (int i=0; i<frequencias.length; i++) {
//          	probfinal[i]= probstotal[i]/soma;
//          }
//          imprimir probfinal - label
//          Imprimir maxprob[0] - resposta final - label
        });
      
	}
}
