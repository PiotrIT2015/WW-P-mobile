package engine.pp.ww_p_mobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private ImageView imageView;
    private Button prevButton;
    private Button nextButton;
    private List<String> imageIds; // Use String for image URLs
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        imageView = findViewById(R.id.imageView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        imageIds = new ArrayList<>();

        // Create an ArrayAdapter using a string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories_array,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                loadImagesByCategory(selectedCategory);
                showImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if no item is selected
            }
        });


        prevButton.setOnClickListener(v -> showPreviousImage());
        nextButton.setOnClickListener(v -> showNextImage());

        // Initial category loading (optional, can choose to load a default if needed)
        String defaultCategory = categorySpinner.getSelectedItem().toString();
        loadImagesByCategory(defaultCategory);
        showImage();
    }

    // Aktualizacja wyświetlanego obrazu
    private void updateImage(){
        if (!imageIds.isEmpty() && currentImageIndex >= 0 && currentImageIndex < imageIds.size()) {
            Glide.with(this)
                    .load(imageIds.get(currentImageIndex))
                    .into(imageView);
        }else{
            imageView.setImageDrawable(null); // Clear ImageView if no image
        }
    }

    // Metoda wyświetlająca poprzedni obraz
    private void showPreviousImage() {
        currentImageIndex--;
        if (currentImageIndex < 0) {
            currentImageIndex = imageIds.size() - 1;
        }
        updateImage();
    }

    // Metoda wyświetlająca następny obraz
    private void showNextImage() {
        currentImageIndex++;
        if (currentImageIndex >= imageIds.size()) {
            currentImageIndex = 0;
        }
        updateImage();
    }


    private void showImage() {
        if (!imageIds.isEmpty() && currentImageIndex >= 0 && currentImageIndex < imageIds.size()) {
            Glide.with(this)
                    .load(imageIds.get(currentImageIndex))
                    .into(imageView);
        } else {
            imageView.setImageDrawable(null); // Clear ImageView if no image
        }
    }

    private void loadImagesByCategory(String category) {
        imageIds.clear();
        String baseUrl = "https://www.google.com/interests/saved/list/?"; // Base URL for Unsplash random images

        String categoryTag;
        switch (category) {
            case "Category 1":
                categoryTag = "mission";
                break;
            case "Category 2":
                categoryTag = "passion";
                break;
            case "Category 3":
                categoryTag = "vacation";
                break;
            case "Category 4":
                categoryTag = "profession";
                break;
            default:
                return; // Handle unexpected categories
        }

        // Add URLs to the list
        for(int i = 0; i < 8; i++){
            imageIds.add(baseUrl + categoryTag);
        }


        currentImageIndex = 0;
    }
}