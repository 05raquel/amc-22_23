import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;




public class App1Learning {
	
	/**
	 * Create the application.
	 */
	public App1Learning() throws IOException {
        initialize();
	}
	
    private final JFileChooser fc = new JFileChooser();
    private final File path = new File(new File(".").getCanonicalPath());
    
    private DataSet d;
    private String fileName_var;
    private int samplelength;
    private int nrvar;
    
	private JFrame frame;
    private JButton Select;
    private File file;
    private JButton back;
    private JButton back2;
    private JLabel Names;
    private JLabel Title;
    private JButton Save;
    private JLabel ISTLogo1;

    private JLabel AMC;
    private JLabel fileNameLabel;
    private JLabel sample_len;
    private JTextField fileName;
    private JLabel nrvariaveis;
    private JLabel error_label;
    private JLabel DONEpic;
    private JLabel savedfilepath;
    

	private void components(JFrame frame) {  //função que cria todos os compponentes de design
		
	//Window 1	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.getContentPane().setLayout(null);
        frame.setBounds(100,100,750,500);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);
        frame.setTitle("Markov Random Field Learning App");
        frame.setResizable(false);
        
        ISTLogo1 = new JLabel(new ImageIcon(Objects.requireNonNull(App1Learning.class.getResource("Resources/ISTLogo.png"))));
	    	
        ISTLogo1.setBounds(261, 0, 183, 203);
        frame.getContentPane().add(ISTLogo1);
        
        Names = new JLabel("Ana Alfaiate 102903 | Catarina Freitas 102076 | Letícia Sousa 102578 | Raquel Coelho 102881");
        Names.setBounds(99, 437, 552, 16);
        Names.setFont(new Font("Tahoma", Font.PLAIN, 13));
        frame.getContentPane().add(Names);

		Select = new JButton("Select Sample File\r\n");
	    Select.setOpaque(false);
	    Select.setBorder(new LineBorder(Color.white));
	    Select.setFont(new Font("Tahoma", Font.PLAIN, 26));
	    Select.setBounds(221, 287, 268, 51);
	    frame.getContentPane().add(Select);
	    
	    Title = new JLabel("Markov Random Field Learning App");
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
	
	    
	//Window 2
	           
        fileNameLabel = new JLabel("Classifier File's Name:");
        fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fileNameLabel.setBounds(128, 271, 210, 20);
        frame.getContentPane().add(fileNameLabel);
        fileNameLabel.setVisible(false);

        fileName = new JTextField();
        fileName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        fileName.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        fileName.setBackground(Color.white);
        fileName.setHorizontalAlignment(SwingConstants.CENTER);
        fileName.setBounds(128, 292, 210, 46);
        frame.getContentPane().add(fileName);
        fileName.setColumns(10);
        fileName.setVisible(false);

        sample_len = new JLabel("");
        sample_len.setHorizontalAlignment(SwingConstants.CENTER);
        sample_len.setBounds(348, 288, 230, 20);
        frame.getContentPane().add(sample_len);
        sample_len.setVisible(false);

        nrvariaveis = new JLabel("");
        nrvariaveis.setHorizontalAlignment(SwingConstants.CENTER);
        nrvariaveis.setBounds(366, 318, 230, 20);
        frame.getContentPane().add(nrvariaveis);
        nrvariaveis.setVisible(false);
        
        back = new JButton(new ImageIcon(Objects.requireNonNull(App1Learning.class.getResource("Resources/BackButton.png"))));
		back.setBorder(null);
	    back.setVisible(false);
	    back.setBounds(10, 10, 36, 36);
	    frame.getContentPane().add(back);
	   
	    Save = new JButton("Save Classifier file");
        Save.setFont(new Font("Tahoma", Font.PLAIN, 16));
        Save.setForeground(new Color(0, 0, 0));
        Save.setOpaque(false);
        Save.setVisible(false);
        Save.setBounds(268, 366, 170, 36);
        //290 137
        frame.getContentPane().add(Save);
        
      //Window 3
        
        DONEpic = new JLabel(new ImageIcon(Objects.requireNonNull(App1Learning.class.getResource("Resources/DonePic.png"))));
        
        DONEpic.setBounds(230, 20, 300, 400);
        frame.getContentPane().add(DONEpic);
        DONEpic.setVisible(false);
        
        savedfilepath = new JLabel("");
        savedfilepath.setBounds(150, 1, 500, 20);
        savedfilepath.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(savedfilepath);
        savedfilepath.setVisible(false);
        
        back2 = new JButton (new ImageIcon(Objects.requireNonNull(App1Learning.class.getResource("Resources/BackButton.png"))));
        back2.setBorder(null);
	    back2.setVisible(false);
	    back2.setBounds(10, 10, 36, 36);
	    frame.getContentPane().add(back2);
	}
	
    private void change12() {
        Select.setVisible(false);
        error_label.setText("");
        back.setVisible(true);
        fileNameLabel.setVisible(true);
        fileName.setText(fileName_var);
        fileName.setVisible(true);
        sample_len.setText("Sample length: "+ samplelength);
        sample_len.setVisible(true);
        nrvariaveis.setText("Number of variables: " + nrvar);
        nrvariaveis.setVisible(true);
        Save.setVisible(true);
    }
    
    private void change23() {
        error_label.setText("");
        back.setVisible(false);
        fileNameLabel.setVisible(false);
        fileName.setVisible(false);
        sample_len.setVisible(false);
        nrvariaveis.setVisible(false);
        Save.setVisible(false);
        DONEpic.setVisible(true);
        Title.setVisible(false);
        nrvariaveis.setVisible(false);
        AMC.setVisible(false);
        error_label.setVisible(false);
        ISTLogo1.setVisible(false); 
        savedfilepath.setText("File '"+fileName_var+"' saved in "+ path);
        savedfilepath.setVisible(true);
        back2.setVisible(true);
    }
	
    private void change21() {
        Select.setVisible(true);
        error_label.setText("");
        back.setVisible(false);
        fileNameLabel.setVisible(false);
        fileName.setVisible(false);
        sample_len.setVisible(false);
        nrvariaveis.setVisible(false);
        Save.setVisible(false);
    }
    
    private void change31() {
        error_label.setText("");
        back2.setVisible(false);
        Title.setVisible(false);
        error_label.setVisible(false);
        ISTLogo1.setVisible(true); 
        Select.setVisible(true);
        DONEpic.setVisible(false);
        Title.setVisible(true);
        AMC.setVisible(true);
        savedfilepath.setVisible(false);
        

    }
        
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fc.setCurrentDirectory(path);
		frame = new JFrame();  //cria uma janela
		components(frame);     //cria na janela criada os elementos que criamos na função componentes
		
		Select.addActionListener(e -> {
            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                try {
                    d = new DataSet(""+file); //construtor a partir do csv file
                    fileName_var = file.getName().substring(0,file.getName().indexOf("."))+".clf";
                    samplelength = d.Samplelength();
                    nrvar = d.NrVariables();
                    change12();
                } catch (Exception e1) {
                    error_label.setText("The dataset could not be imported");
                }
            }
        });
		
        back.addActionListener(e -> {  //ao clicar no botão de retroceder vai fazer isto
            int goback = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back?\nAll data will be lost", "", JOptionPane.YES_NO_OPTION);
            if(goback == JOptionPane.YES_OPTION){
            	d = new DataSet(); change21();  // mudar para a janela inicial
            }
        });
        
        Save.addActionListener(e -> {

            // Learning Code
        	long startTime = System.nanoTime();
            fileName_var = fileName.getText();
            int [] doms = d.getDomains(); //array com o número de valores de cada caracteristica            
            int domClasses = d.getClassDomain(); //nr de classes = domClasses
           
            double [] freq = new double [domClasses]; //array com as diferentes classes, depois preenchemos com freq
            MRFT [] arrayMRFTfibers = new MRFT [domClasses];
            
            DataSet [] fibs = d.Fibers();             //dividir dataset em fibers
            for (int i=0; i < domClasses; i++) {
            	DataSet fiber = fibs[i];
                freq[i]= (double) fiber.Samplelength() / (double) samplelength;  //frequencia =nr de vetores dessa classe/nr de vetores total
                arrayMRFTfibers[i] = new MRFT(fiber, ChowLiu.Chow_liu(fiber), doms);
            }

            Classifier classificador = new Classifier(arrayMRFTfibers, freq, nrvar);
            try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName_var));  //guardar no disco
                os.writeObject(classificador);
                os.close();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            //
        	change23();
        	long endTime   = System.nanoTime();
    		long totalTime = endTime - startTime;
    		System.out.println(totalTime);

        });
        
        back2.addActionListener(e -> {  
            int goback = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back?\nAll data will be lost", "", JOptionPane.YES_NO_OPTION);
            if(goback == JOptionPane.YES_OPTION){
            	d = new DataSet(); change31();  // mudar para a janela inicial
            }
        });
	}

	/**Launch the application */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App1Learning window = new App1Learning();
					window.frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
