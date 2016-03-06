package kkdev.kksystem.kkcarandroid.manager.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kkdev.kksystem.base.classes.odb2.ODBConstants;
import kkdev.kksystem.base.classes.odb2.PinOdb2Data;

/**
 * Created by blinov_is on 01.12.2015.
 */
public class KKDiagInfo {
    public String MILString;
    public List<KKDTCCode> CurrentDTC;
    public int Timestamp;
    public boolean DataFromBT;

    public ArrayList<HashMap<String, String>>  GetDTCErrArray()
    {
        ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> Item;
        for (KKDTCCode KK:CurrentDTC)
        {
            Item= new HashMap<String, String>();
            Item.put("DTC_ID",KK.value);
            Item.put("Description",KK.localdesc);
            myArrList.add(Item);
        }
        return myArrList;

    }

    public void InitValues()
    {

        if (CurrentDTC!=null && CurrentDTC.size()==0)
        {
            MILString="OK";
        }
        else
        {
            MILString="CHECK ENGINE";

        }
    }

    public static KKDiagInfo FillFromPinODB2(PinOdb2Data Dat)
    {
        KKDiagInfo Ret;
        Ret=new KKDiagInfo();

        List<KKDTCCode> RetCE;
        RetCE=new ArrayList<>();

        if (Dat.Odb2DataType== ODBConstants.KK_ODB_DATATYPE.ODB_DIAG_CE_ERRORS)
        {
            for (Integer Pfx:Dat.ODBData.GetCEError().keySet())
            {
                for (Byte Err:Dat.ODBData.GetCEError().get(Pfx))
                {
                    RetCE.add(new KKDTCCode(Pfx.toString(),Err.toString(),"0"));
                }

            }

            Ret.CurrentDTC=RetCE;
        }

        Ret.Timestamp=0;
        Ret.DataFromBT=true;
        Ret.InitValues();

        return  Ret;
    }

}
