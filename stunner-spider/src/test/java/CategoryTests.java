import com.cherry.stunner.data.po.Category;
import com.cherry.stunner.data.service.impl.mapper.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
@Transactional
@Rollback
public class CategoryTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testInsert() {

        Category category = new Category();
        category.setTitle("明星");

        int rows = categoryMapper.insertCategory(category);
        assertEquals(1, rows);

        Category cate = new Category();
        cate.setTitle("明星");
        rows = categoryMapper.insertCategory(cate);
        assertEquals(0, rows);


        category = categoryMapper.selectCategoryById(category.getId());
        assertNotNull(category);

        cate = categoryMapper.selectCategoryByTitle("明星");
        assertNotNull(cate);

    }

}
