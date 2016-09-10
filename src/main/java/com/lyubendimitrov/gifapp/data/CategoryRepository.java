package com.lyubendimitrov.gifapp.data;

import com.lyubendimitrov.gifapp.model.Category;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:lyuben.dimitrov@comsysto.com">dimitrov</a>
 * @since 10.09.2016
 */

@Component
public class CategoryRepository {
    private static final List<Category> ALL_CATEGORIES = Arrays.asList(
        new Category(1, "Funny"),
        new Category(2, "NSFW"),
        new Category(3, "WTF")
    );

    public Category findById(int id) {
        for(Category category : ALL_CATEGORIES) {
            if(category.getId() == (id)) {
                return category;
            }
        }
        return null;
    }

    public List<Category> findAllCategories() {
        return ALL_CATEGORIES;
    }
}