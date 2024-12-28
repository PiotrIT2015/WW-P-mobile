package engine.pp.ww_p_mobile;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private ImageView imageView;
    private Button prevButton;
    private Button nextButton;
    private List<Integer> imageIds;
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
                loadImageIdsByCategory(selectedCategory);
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
        loadImageIdsByCategory(defaultCategory);
        showImage();
    }

    // Aktualizacja wyświetlanego obrazu
    private void updateImage(){
        imageView.setImageResource(imageIds.get(currentImageIndex));
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
            imageView.setImageResource(imageIds.get(currentImageIndex));
        } else {
            imageView.setImageDrawable(null); // Clear ImageView if no image
        }
    }

    private void loadImageIdsByCategory(String category) {
        imageIds.clear();
        Resources resources = getResources();
        String packageName = getPackageName();

        String basePath = "drawable/img/";
        String categoryFolderName;

        // Map categories to folder names
        switch (category) {
            case "Category 1":
                categoryFolderName = "mission";
                break;
            case "Category 2":
                categoryFolderName = "passion";
                break;
            case "Category 3":
                categoryFolderName = "vacation";
                break;
            case "Category 4":
                categoryFolderName = "profession";
                break;
            default:
                return; // Handle unexpected categories
        }


        //Loop and create paths
        for (int i = 1; i <= 8; i++) {
            String fullPath = basePath + categoryFolderName + "/img" + i;
            int resourceId = resources.getIdentifier(fullPath, null, packageName);

            if(resourceId !=0) {
                imageIds.add(resourceId);
            }

        }

        currentImageIndex = 0;
    }

}