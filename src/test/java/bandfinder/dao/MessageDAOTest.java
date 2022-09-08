package bandfinder.dao;

import bandfinder.models.Message;
import bandfinder.models.MessageViewModel;
import bandfinder.models.User;
import bandfinder.serviceimplementations.HashMapUserDAO;
import bandfinder.serviceimplementations.TreeMapMessageDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

class MessageDAOTest {
    private MessageDAO dao;
    private UserDAO userDAO;

    private final User john = new User(1,"johndoe@mail", "pass", "John", "Doe", null);
    private final User foo = new User(2, "foo@mail", "pass", "FooName", "FooSurname", "FooStage");
    private final User bar = new User(3,"bar@mail", "pass", "BarName", "BarSurname", "BarStage");

    @BeforeEach
    public void setUp() {
        userDAO = new HashMapUserDAO();
        userDAO.create(john);
        userDAO.create(foo);
        userDAO.create(bar);
        this.dao = new TreeMapMessageDAO(userDAO);
    }

    @Test
    public void testOneConversation() {
        // create
        Message m1 = new Message(1,1, 2, "c1", Calendar.getInstance().getTime());
        Message m2 = new Message(2,1, 2, "c2", Calendar.getInstance().getTime());
        Message m3 = new Message(3,2, 1, "c3", Calendar.getInstance().getTime());
        Message m4 = new Message(4,1, 2, "c4", Calendar.getInstance().getTime());
        Assertions.assertEquals(m1, dao.create(m1));
        Assertions.assertEquals(m2, dao.create(m2));
        Assertions.assertEquals(m3, dao.create(m3));
        Assertions.assertEquals(m4, dao.create(m4));

        // delete()
        Assertions.assertTrue(dao.delete(4));
        Assertions.assertFalse(dao.delete(4));

        // update()
        String newContent = "updated";
        m3.setContent(newContent);
        Assertions.assertEquals(newContent, dao.update(m3).getContent());

        // getById()
        Assertions.assertNull(dao.getById(4));
        Assertions.assertEquals(m3, dao.getById(3));

        // getAll()
        List<Message> msgsSoFar = dao.getAll();
        Assertions.assertEquals(3, msgsSoFar.size());

        MessageViewModel m1ViewModel = new MessageViewModel(1, john.getFullName(), foo.getFullName(), "c1", MessageViewModel.formatDate(m1.getTimestamp()));
        MessageViewModel m2ViewModel = new MessageViewModel(2, john.getFullName(), foo.getFullName(), "c2", MessageViewModel.formatDate(m2.getTimestamp()));
        MessageViewModel m3ViewModel = new MessageViewModel(3, foo.getFullName(), john.getFullName(), "updated", MessageViewModel.formatDate(m3.getTimestamp()));

        // getNewMessages()
        List<MessageViewModel> newMsgs1 = dao.getNewMessages(1, 2, 2);
        List<MessageViewModel> newMsgs2 = dao.getNewMessages(2, 1, 2);
        Assertions.assertEquals(2, newMsgs1.size());
        Assertions.assertEquals(newMsgs1, newMsgs2);
        Assertions.assertEquals(m3ViewModel, newMsgs1.get(0));
        Assertions.assertEquals(m2ViewModel, newMsgs1.get(1));

        // getMessagesBefore()
        List<MessageViewModel> lastMsgs1 = dao.getMessagesBefore(1, 2, 3, 2);
        List<MessageViewModel> lastMsgs2 = dao.getMessagesBefore(2, 1, 3, 2);
        Assertions.assertEquals(2, lastMsgs1.size());
        Assertions.assertEquals(lastMsgs1, lastMsgs2);
        Assertions.assertEquals(m2ViewModel, lastMsgs1.get(0));
        Assertions.assertEquals(m1ViewModel, lastMsgs2.get(1));
    }

    @Test
    public void testMultipleConversations() {
        List<Message> msgs = List.of(
                new Message(1,1, 2, "c1", Calendar.getInstance().getTime()),
                new Message(2,2, 3, "c2", Calendar.getInstance().getTime()),
                new Message(3,3, 2, "c3", Calendar.getInstance().getTime()),
                new Message(4,3, 2, "c4", Calendar.getInstance().getTime()),
                new Message(5,2, 1, "c5", Calendar.getInstance().getTime()),
                new Message(6,2, 1, "c6", Calendar.getInstance().getTime()),
                new Message(7,2, 3, "c7", Calendar.getInstance().getTime()),
                new Message(8,1, 3, "c8", Calendar.getInstance().getTime())
        );
        for(Message m : msgs) {
            dao.create(m);
        }

        List<MessageViewModel> newMsgs12 = dao.getNewMessages(1, 2, 2);
        List<MessageViewModel> newMsgs21 = dao.getNewMessages(2, 1, 2);
        Assertions.assertEquals(2, newMsgs12.size());
        Assertions.assertEquals(newMsgs12, newMsgs21);
        Assertions.assertEquals(6, newMsgs12.get(0).id());
        Assertions.assertEquals(5, newMsgs12.get(1).id());

        List<MessageViewModel> msgsBefore23 = dao.getMessagesBefore(2, 3, 7, 2);
        List<MessageViewModel> msgsBefore32 = dao.getMessagesBefore(3, 2, 7, 2);
        Assertions.assertEquals(2, msgsBefore23.size());
        Assertions.assertEquals(msgsBefore23, msgsBefore32);
        Assertions.assertEquals(4, msgsBefore23.get(0).id());
        Assertions.assertEquals(3, msgsBefore23.get(1).id());

        List<MessageViewModel> msgsBeforeBeginning = dao.getMessagesBefore(2,1, 6, 3);
        Assertions.assertEquals(2, msgsBeforeBeginning.size());
    }

    @Test
    public void testMessageViewModel() {
        String content = "Some msg content";
        Message m = new Message(1, 1, 2, content, Calendar.getInstance().getTime());
        dao.create(m);
        MessageViewModel correctViewModel = dao.getMessageViewForId(1);
        MessageViewModel expectedViewModel = new MessageViewModel(
                1,
                john.getFullName(),
                foo.getFullName(),
                content,
                MessageViewModel.formatDate(m.getTimestamp()));
        Assertions.assertEquals(expectedViewModel, correctViewModel);
    }
}