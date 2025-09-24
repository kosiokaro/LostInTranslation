package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JSONTranslator translator = new JSONTranslator("sample.json");
            CountryCodeConverter cc = new CountryCodeConverter();
            LanguageCodeConverter lc = new LanguageCodeConverter();

            //country
            JPanel countryPanel = new JPanel();
            JComboBox<String> countryBox = new JComboBox<>();
            for(String countryCode : translator.getCountryCodes()) {
                String cn = cc.fromCountryCode(countryCode);
                countryBox.addItem(cn);
                //System.out.println("Code: " + countryCode + " -> Name: " + cn);

            }
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryBox);

            //language
            JPanel languagePanel = new JPanel();
            JComboBox<String> languageBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                String lang = lc.fromLanguageCode(languageCode);
                languageBox.addItem(lang);
            }
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageBox);


//            JPanel buttonPanel = new JPanel();
//            JButton submit = new JButton("Submit");
//            buttonPanel.add(submit);

            JPanel resultPanel = new JPanel();
            JLabel resultLabel = new JLabel("Translation:");
            JLabel resultL = new JLabel();
            resultPanel.add(resultLabel);
            resultPanel.add(resultL);


            // 创建更新翻译的方法
            Runnable updateTranslation = () -> {
                String selectedCountryName = (String) countryBox.getSelectedItem();
                String selectedLanguageName = (String) languageBox.getSelectedItem();

                if (selectedCountryName != null && selectedLanguageName != null) {
                    String countryCode = cc.fromCountry(selectedCountryName);
                    String languageCode = lc.fromLanguage(selectedLanguageName);

                    if (countryCode != null && languageCode != null) {
                        String result = translator.translate(countryCode, languageCode);
                        if (result != null) {
                            resultL.setText(result);
                        }
                    }
                }
            };

            //listener for language and country
            countryBox.addActionListener(e -> {
                // 更新翻译结果
                updateTranslation.run();
            });

            languageBox.addActionListener(e -> {
                updateTranslation.run();
            });


            // adding listener for when the user clicks the submit button
//            submit.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String language = (String) languageBox.getSelectedItem();
//                    String country = (String) countryBox.getSelectedItem();
//
//                    // for now, just using our simple translator, but
//                    // we'll need to use the real JSON version later.
//                    //Translator translator = new CanadaTranslator();
//                    String languageCode = lc.fromLanguage(language);
//                    String countryCode = cc.fromCountry(country);
//
//
//                    String result = translator.translate(countryCode, languageCode);
//                    if (result == null) {
//                        result = "no translation found!";
//                    }
//                    resultLabel.setText(result);
//
//                }
//
//            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);



        });
    }
}
