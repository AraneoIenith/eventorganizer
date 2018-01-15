package de.hsba.two.organizer.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    private User testUser = new User("12345");

    @Before
    public void setUp() throws Exception {
        entityManager.persist(testUser);
    }

    @Test
    public void testChangeUsername() throws Exception {

        //wenn
        userService.changeUsername(testUser.getUsername(), "54321");

        //dann
        assertEquals(userService.getUser("54321"), testUser);

    }

    @Test
    public void testCreateWithSameUserName() throws Exception {

        //wenn
        String test = userService.createUser("12345", "bla", "bla", "USER", true);

        //dann
        assertTrue(test.equals("userexist"));
    }


}
