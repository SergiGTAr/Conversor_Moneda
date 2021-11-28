package facci.com.conversorac;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLtoSQLite {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(url.openStream());
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getElementsByTagName("Cube");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                list.add(element.getAttribute("currency") + " " + element.getAttribute("rate"));
            }
        }
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");
             Statement statement = connection.createStatement();) {
            statement.execute("DROP TABLE IF EXISTS Currency");
            statement.execute("CREATE TABLE Currency (id INTEGER PRIMARY KEY, currency VARCHAR(255), rate FLOAT)");
            for (String s : list) {
                String[] split = s.split(" ");
                statement.execute("INSERT INTO Currency (currency, rate) VALUES ('" + split[0] + "', " + split[1] + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
