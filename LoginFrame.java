import javax.swing.*;import java.awt.*;
public class LoginFrame extends JFrame {
    private JTextField user=new JTextField("admin"); private JPasswordField pass=new JPasswordField();
    private static String password="admin123";
    public LoginFrame(){
        setTitle("GrandStay | Admin Login");setSize(400,260);setLocationRelativeTo(null);setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel p=new JPanel(new GridLayout(0,1,8,8));p.setBorder(BorderFactory.createEmptyBorder(25,30,25,30));
        JLabel title=new JLabel("GRANDSTAY HOTEL",SwingConstants.CENTER);title.setFont(new Font("SansSerif",Font.BOLD,20));
        p.add(title);p.add(new JLabel("Username"));p.add(user);p.add(new JLabel("Password"));p.add(pass);
        JButton login=new JButton("Login"); JButton forgot=new JButton("Forgot Password");
        JPanel buttons=new JPanel();buttons.add(login);buttons.add(forgot);p.add(buttons);add(p);
        login.addActionListener(e->{
            if(user.getText().trim().equals("admin")&&new String(pass.getPassword()).equals(password)){
                new MainFrame().setVisible(true);dispose();
            }else JOptionPane.showMessageDialog(this,"Invalid username or password.");
        });
        forgot.addActionListener(e->{
            String answer=JOptionPane.showInputDialog(this,"Recovery question: What is the hotel name?");
            if(answer!=null&&answer.trim().equalsIgnoreCase("GrandStay")){
                String np=JOptionPane.showInputDialog(this,"Enter new password:");
                if(np!=null&&!np.trim().isEmpty()){password=np;JOptionPane.showMessageDialog(this,"Password reset for this session. Default password will return when app restarts.");}
            }else if(answer!=null)JOptionPane.showMessageDialog(this,"Incorrect recovery answer.");
        });
    }
}
