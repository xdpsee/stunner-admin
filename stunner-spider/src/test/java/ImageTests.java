import com.cherry.stunner.data.enums.ImageStatus;
import com.cherry.stunner.data.po.Image;
import com.cherry.stunner.data.service.impl.mapper.ImageMapper;
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
public class ImageTests {


    @Autowired
    private ImageMapper imageMapper;

    @Test
    public void testMethods() {


        Image image = new Image();
        image.setAlbumId(1L);
        image.setOriginUrl("http://test.jpg");

        assertEquals(1, imageMapper.insert(image));

        List<Image> images = imageMapper.selectByAlbumId(1L, null);
        assertTrue(images.isEmpty());

        imageMapper.updateStatus(image.getId(), ImageStatus.READY);

        images = imageMapper.selectByAlbumId(1L, null);
        assertTrue(!images.isEmpty());


    }


}
