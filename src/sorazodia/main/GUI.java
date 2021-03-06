package sorazodia.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GUI extends JPanel implements ActionListener, FocusListener {

	private static final long serialVersionUID = -8663347066048248839L;

	private JButton hash = new JButton("Choose File");
	private JTextField compare = new JTextField();
	private JTextField output = new JTextField();
	
	private JLabel generatedHash = new JLabel("Generated Hash");
	private JLabel expectedHash = new JLabel("Expected Hash");
	
	private JRadioButton md5 = new JRadioButton(Hash.MD5.toString());
	private JRadioButton sha1 = new JRadioButton(Hash.SHA1.toString());
	private JRadioButton sha224 = new JRadioButton(Hash.SHA224.toString());
	private JRadioButton sha256 = new JRadioButton(Hash.SHA256.toString());
	private JRadioButton sha384 = new JRadioButton(Hash.SHA384.toString());
	private JRadioButton sha512 = new JRadioButton(Hash.SHA512.toString());

	private JRadioButton[] list = { md5, sha1, sha224, sha256, sha384, sha512 };

	private String hashType = Hash.MD5.toString();
	private JFileChooser fc = new JFileChooser();
	private JFrame frame;

	public void draw() {
		compare.setMaximumSize(new Dimension(compare.getMaximumSize().width, 25));
		compare.setToolTipText("The generated hash will be compared to the hash placed, if any, in this field. To get a accurate result, please ensure that the correct hashing algorithm has been selected.");
		output.setMaximumSize(new Dimension(output.getMaximumSize().width, 25));
		output.setToolTipText("The hash generated will be shown here, if there's a hash-to-compare placed on the field adove this, \nthen it will turn green if it's a match and red otherwises.");
		output.setBackground(Color.WHITE);
		output.addFocusListener(this);
		output.setEditable(false);
		this.add(expectedHash);
		this.add(compare);
		this.add(generatedHash);
		this.add(output);

		hash.setToolTipText("The hash will be generated after an file is choosen");
		hash.setActionCommand("compute");
		hash.addActionListener(this);
		this.add(hash);

		ButtonGroup group = new ButtonGroup();
		for (int x = 0; x < list.length; x++) {
			group.add(list[x]);
			if (list[x] == md5)
				list[x].setSelected(true);
			list[x].setActionCommand(Hash.values()[x].toString());
			list[x].addActionListener(this);

			this.add(list[x]);
		}

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(600, 285));
		frame = new JFrame("File Checksum");
		frame.setMinimumSize(new Dimension(300, 285));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		switch (event.getActionCommand()) {
		case "MD5":
			hashType = Hash.MD5.toString();
			break;
		case "SHA-1":
			hashType = Hash.SHA1.toString();
			break;
		case "SHA-224":
			hashType = Hash.SHA224.toString();
			break;
		case "SHA-256":
			hashType = Hash.SHA256.toString();
			break;
		case "SHA-384":
			hashType = Hash.SHA384.toString();
			break;
		case "SHA-512":
			hashType = Hash.SHA512.toString();
			break;
		case "compute":
			String checksum = "";

			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int value = fc.showDialog(this, "Select");
			
			if (value == JFileChooser.APPROVE_OPTION)
				checksum = HashGenerator.getHash(fc.getSelectedFile()
						.getAbsolutePath(), hashType);
			
			if (!compare.getText().isEmpty()) {
				if (checksum.compareTo(compare.getText().trim()) == 0)
					output.setBackground(Color.GREEN);
				else
					output.setBackground(Color.RED);
			}

			output.setText(checksum);
			break;
		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (output.isFocusOwner())
			output.selectAll();

		if (compare.isFocusOwner())
			compare.selectAll();
	}

	@Override
	public void focusLost(FocusEvent e) {

	}

	private enum Hash {
		MD5("MD5"), SHA1("SHA-1"), SHA224("SHA-224"), SHA256("SHA-256"), SHA384(
				"SHA-384"), SHA512("SHA-512");

		private String algorithm;

		Hash(String name) {
			algorithm = name;
		}

		@Override
		public String toString() {
			return algorithm;
		}
	}

}
