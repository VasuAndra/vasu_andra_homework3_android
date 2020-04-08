package com.example.android_tema3_vasu_andra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android_tema3_vasu_andra.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private RecyclerView usersList;
    private RecyclerView.Adapter RVadapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> users= new ArrayList<>();
    private View.OnClickListener onClickListener;
    private ToDosFragment toDosFragment = new ToDosFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getUsers();

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        usersList =  view.findViewById(R.id.usersRV);
        usersList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        usersList.setLayoutManager((layoutManager));

        onClickListener = new myUserOnClickListener();
        RVadapter = new UsersAdapter(users,onClickListener);
        usersList.setAdapter(RVadapter);

        return view;
    }

    private void getUsers()
    {
        String url = "https://my-json-server.typicode.com/MoldovanG/JsonServer/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){
                    try {
                        User user = new User().fromJSON(response.getJSONObject(i));
                        ((UsersAdapter)RVadapter).addItem(user);

                    } catch (JSONException e){
                        e.printStackTrace();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    public  class myUserOnClickListener implements View.OnClickListener {

        private int Id;

        public void setId(int id) {

            Id = id;
        }

        @Override
        public void onClick(View v) {

            toDosFragment.setUserId(Id);
            ((MainActivity)getActivity()).replaceFragment(toDosFragment);
        }
    }

}
