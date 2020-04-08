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
import com.example.android_tema3_vasu_andra.models.ToDo;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class ToDosFragment extends Fragment {

    private  int userId;
    private RecyclerView toDosList;
    private RecyclerView.Adapter RVadapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ToDo> toDos= new ArrayList<>();
    private DateTimeFragment dateTimeFragment = new DateTimeFragment();

    public void setUserId(int userId) {

        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_todos, container, false);

        getToDos(userId);

        toDosList =  view.findViewById(R.id.todosRV);
        toDosList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        toDosList.setLayoutManager(layoutManager);

        View.OnClickListener onClickListener = new myToDoClickListener();

        RVadapter = new ToDosAdapter(toDos, onClickListener);
        toDosList.setAdapter(RVadapter);

        return view;
    }
    private void getToDos(int id)
    {
        String url = "https://jsonplaceholder.typicode.com/todos?userId=";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url+ id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){
                    try {
                        ToDo toDo = new ToDo().fromJSON(response.getJSONObject(i));
                        ((ToDosAdapter)RVadapter).addItem(toDo);

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
    public  class myToDoClickListener implements View.OnClickListener {

        private String title;

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public void onClick(View v) {

            dateTimeFragment.setToDoTitle(title);

            ((MainActivity)getActivity()).replaceFragment(dateTimeFragment);
        }
    }
}
