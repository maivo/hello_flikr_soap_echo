package pee.actionbar.app;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pvu_asus on 21/05/2015.
 */
public class SiteUrlSet extends DefaultHandler {

    private static final String TAG = "UrlSetHandler";
    String mTempContent;

    SiteUrl mTempSiteUrl;

    List<SiteUrl> mSiteUrlList;

    public SiteUrlSet(){
        mSiteUrlList = new ArrayList<SiteUrl>();
    }

    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes atts) {
        //Log.i(TAG, "inside SAXTreeStructure.startElement(). rawName: " + rawName);

        if (rawName.equalsIgnoreCase("url")) {
            mTempSiteUrl = new SiteUrl();
        }
    }

    public void endElement(String namespaceURI, String localName,
                           String rawName) {
        //Log.i(TAG, "inside SAXTreeStructure.endElement(). rawName: "+rawName);


        if (rawName.equalsIgnoreCase("loc")) {

            mTempSiteUrl.setLoc(this.mTempContent);
        }

        if (rawName.equalsIgnoreCase("lastmod")) {
            mTempSiteUrl.setLastMod(this.mTempContent);
        }

        if (rawName.equalsIgnoreCase("changefreq")) {
            mTempSiteUrl.setChangeFreq(this.mTempContent);
        }

        if (rawName.equalsIgnoreCase("url")) {
            this.mSiteUrlList.add(mTempSiteUrl);
        }


    }

    public void characters(char[] data, int off, int length) {
        //Log.i(TAG, "inside SAXTreeStructure.characters().");
        mTempContent = new String(data, 0, length);
    }

    public List<SiteUrl> getSiteUrlList(){
        return this.mSiteUrlList;
    }
}
