package kkdev.kksystem.kkcarandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import kkdev.kksystem.kkcarandroid.manager.DiagOperations;
import kkdev.kksystem.kkcarandroid.manager.callback.IDiagUI;
import kkdev.kksystem.kkcarandroid.manager.types.KKDiagInfo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frg_Diag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_Diag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_Diag extends Fragment implements IDiagUI {

    KKDiagInfo CurrDI;
    SimpleAdapter ErrorAdapter;
    TextView txtName=(TextView)getView().findViewById(R.id.txt_Diag_CEState);

    ImageView imgConnBT=(ImageView)getView().findViewById(R.id.img_diag_connectiontype_bt);
    ImageView imgConnINET=(ImageView)getView().findViewById(R.id.img_diag_connectiontype_bt);

    private OnFragmentInteractionListener mListener;

    public static frg_Diag newInstance() {
        frg_Diag fragment = new frg_Diag();

        return fragment;
    }

    public frg_Diag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg__diag, container, false);



    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DiagOperations.RegisterCallback(this);
        DiagOperations.RequestDiagCE();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DiagOperations.UnRegisterCallback();
        mListener = null;
    }

    @Override
    public void UpdateErrorsList(KKDiagInfo DiagInfo) {
        CurrDI=DiagInfo;
        ExecRefreshInfo.sendEmptyMessage(1);
    }

    @Override
    public void UpdateMonitorInfo(KKDiagInfo DiagInfo) {
        CurrDI=DiagInfo;
        ExecRefreshInfo.sendEmptyMessage(1);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btnRefresh=(Button)getView().findViewById(R.id.btnCERefresh);
        txtName=(TextView)getView().findViewById(R.id.txt_Diag_CEState);
        imgConnBT=(ImageView)getView().findViewById(R.id.img_diag_connectiontype_bt);
        imgConnINET=(ImageView)getView().findViewById(R.id.img_diag_connectiontype_bt);
        //
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiagOperations.RequestDiagCE();
            }
        });
    }

    Handler ExecRefreshInfo = new Handler() {

        public void handleMessage(android.os.Message msg) {
            RefreshInfo(CurrDI);
        }

        private void RefreshInfo( KKDiagInfo DI)
        {
            //
            txtName.setText(DI.MILString);
            //
            //
            if (DI.DataFromBT) {
                imgConnBT.setVisibility(View.VISIBLE);
                imgConnINET.setVisibility(View.INVISIBLE);
            }
            else
            {
                imgConnBT.setVisibility(View.INVISIBLE);
                imgConnINET.setVisibility(View.VISIBLE);

            }
            if (ErrorAdapter==null) {
                //
                // находим список
                ListView lvMain = (ListView) getView().findViewById(R.id.lst_Diag_DTC);
                //
                ErrorAdapter = new SimpleAdapter(getView().getContext(), DI.GetDTCErrArray(), android.R.layout.simple_list_item_2,
                        new String[]{"DTC_ID", "Description"},
                        new int[]{android.R.id.text1, android.R.id.text2});

                lvMain.setAdapter(ErrorAdapter);
            }
            else
            {
                ErrorAdapter.notifyDataSetChanged();
            }

        }
    };



}
