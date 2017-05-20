import com.cherry.stunner.data.po.Album;
import com.cherry.stunner.data.service.impl.mapper.AlbumMapper;
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
public class AlbumTests {

    @Autowired
    private AlbumMapper albumMapper;

    @Test
    public void testInsertAlbum() {

        Album album = new Album();
        album.setOriginUrl("url.origin");
        album.setOriginTitle("title.origin");
        album.setOriginCoverUrl("cover.url.origin");

        int rows = albumMapper.insertAlbum(album);
        assertEquals(1, rows);

        album = albumMapper.selectAlbumById(album.getId());
        assertNotNull(album);

    }


}
