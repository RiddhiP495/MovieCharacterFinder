package com.example.a1.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a1.OnCharacterClickListener;
import com.example.a1.databinding.ItemCharacterListBinding;
import com.example.a1.models.CharacterName;
import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private final Context context;
    private final ArrayList<CharacterName> characterNameArrayList;
    ItemCharacterListBinding binding;
    private final OnCharacterClickListener clickListener;

    public CharacterAdapter(Context context, ArrayList<CharacterName> characterName, OnCharacterClickListener clickListener){
        this.context = context;
        this.characterNameArrayList = characterName;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterViewHolder(ItemCharacterListBinding.inflate(LayoutInflater
                .from(context),parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        CharacterName currentName = characterNameArrayList.get(position);
        holder.bind(context,currentName,clickListener);
    }

    @Override
    public int getItemCount() {
        return this.characterNameArrayList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder{
        ItemCharacterListBinding itemCharacterListBinding;
        public CharacterViewHolder(ItemCharacterListBinding binding){
            super(binding.getRoot());
            this.itemCharacterListBinding = binding;
        }

        public void bind(Context context, CharacterName currentName,OnCharacterClickListener clickListener){
            itemCharacterListBinding.tvName.setText(currentName.getName());

            if(currentName.getSpecies().isEmpty()){

                itemCharacterListBinding.tvSpecies.append("Human");
                itemCharacterListBinding.tvSpecies.setTextColor(Color.BLUE);
            }else {

                itemCharacterListBinding.tvSpecies.append("Non Human");
                itemCharacterListBinding.tvSpecies.setTextColor(Color.RED);
            }

            itemCharacterListBinding.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onCharacterClicked(currentName);
                }
            });
        }
    }
}
