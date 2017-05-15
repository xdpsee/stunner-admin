import com.cherry.stunner.data.po.Tag;
import com.cherry.stunner.data.service.impl.mapper.TagMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
@Transactional
@Rollback
public class TagTests {

    @Autowired
    private TagMapper tagMapper;


    @Test
    public void testMethods() {

        Tag tag = new Tag();
        tag .setTitle("测试");

        assertEquals(1, tagMapper.insert(tag));

    }
    @Test
    public void testIgnore() {

        Tag tag = new Tag();
        tag .setTitle("测试");

        assertEquals(1, tagMapper.insert(tag));

        tag = new Tag();
        tag .setTitle("测试");

        int rows = tagMapper.insert(tag);
        assertEquals(0, rows);

    }

    @Test
    public void testImageTags() {

        Tag tag = new Tag();
        tag .setTitle("测试");

        assertEquals(1, tagMapper.insert(tag));

        tagMapper.insertImageTag(1L, tag.getId());

        List<Tag> tags = tagMapper.selectByImageId(1L);

        assertTrue(tags.stream().anyMatch(e->e.getId().equals(tag.getId())));

    }

}
