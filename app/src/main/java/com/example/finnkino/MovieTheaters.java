package com.example.finnkino;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieTheaters {
    private final ArrayList<MovieTheater> theaterList;
    private static MovieTheaters mt = null;

    public static MovieTheaters getInstance() {
        if (mt == null) {
            mt = new MovieTheaters();
        }
        return mt;
    }


    private MovieTheaters() {
        theaterList = new ArrayList<MovieTheater>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            doc.getDocumentElement().getElementsByTagName("Name");
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getElementsByTagName("ID").item(0)
                            .getTextContent();
                    String place = element.getElementsByTagName("Name").item(0)
                            .getTextContent();
                    MovieTheater theater = new MovieTheater(id, place);

                    theaterList.add(theater);
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MovieTheater> getTheaterList() {
        return theaterList;
    }
}
