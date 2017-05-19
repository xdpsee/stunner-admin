import com.cherry.stunner.data.enums.TagType;
import com.cherry.stunner.data.po.Tag;
import com.cherry.stunner.data.service.impl.mapper.TagMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        tag.setType(TagType.NORMAL);
        tag .setTitle("测试");

        assertEquals(1, tagMapper.insert(tag));

    }
    @Test
    public void testIgnore() {

        Tag tag = new Tag();
        tag.setType(TagType.NORMAL);
        tag .setTitle("测试");

        assertEquals(1, tagMapper.insert(tag));

        tag = new Tag();
        tag.setType(TagType.NORMAL);
        tag .setTitle("测试");

        int rows = tagMapper.insert(tag);
        assertEquals(0, rows);

    }

    @Test
    public void testTags() {

        Tag tag = new Tag();
        tag.setType(TagType.NORMAL);
        tag .setTitle("测试");

        assertEquals(1, tagMapper.insert(tag));

        long tagId = tag.getId();

        tagMapper.insertCategoryTag(0L, tagId, new Date());

        List<Tag> tags = tagMapper.selectCategoryTags(0L);
        assertEquals(1, tags.size());

        tagMapper.insertAlbumTag(0L, tagId);

        tags = tagMapper.selectAlbumTags(0L);
        assertEquals(1, tags.size());
    }

}
