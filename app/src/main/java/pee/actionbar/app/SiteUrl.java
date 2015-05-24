package pee.actionbar.app;

/**
 * Created by pvu_asus on 21/05/2015.
 */
public class SiteUrl {
    String mLoc;
    String mLastMod;
    String mChangeFreq;

    public String toString(){
        return "loc: "+mLoc +", mLastMod: "+ mLastMod +", changeFreq: "+ mChangeFreq;
    }

    public String getLoc() {
        return mLoc;
    }

    public void setLoc(String loc) {
        mLoc = loc;
    }

    public String getLastMod() {
        return mLastMod;
    }

    public void setLastMod(String lastMod) {
        mLastMod = lastMod;
    }

    public String getChangeFreq() {
        return mChangeFreq;
    }

    public void setChangeFreq(String changeFreq) {
        mChangeFreq = changeFreq;
    }
}
