package com.skypan.wbse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypan.wbse.adapter.cardAdapter;
import com.skypan.wbse.retrofit.Article;
import com.skypan.wbse.retrofit.RetrofitManager;
import com.skypan.wbse.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularlFragment extends Fragment {
    public PopularlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_personal, container, false);
        RecyclerView rv_1 = root.findViewById(R.id.rv_1);

        RetrofitService retrofitService = RetrofitManager.getInstance().getService();

        Call<List<Article>> call = retrofitService.popularArticle();
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "伺服器錯誤，請稍後再試", Toast.LENGTH_SHORT).show();
                }
                List<Article> article = response.body();

                System.out.println(article.get(0).getArticleContent());

                //System.out.println(article.get(2).getArticleComments().get(0).toString());

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_1.setLayoutManager(linearLayoutManager);
                rv_1.setAdapter(new cardAdapter(getActivity(),article));
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

            }
        });

        return root;
    }

}
