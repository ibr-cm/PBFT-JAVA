package de.teamproject16.pbft.Messages;

import org.json.JSONObject;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static de.teamproject16.pbft.Messages.Types.PROPOSE;
import de.teamproject16.pbft.NormalCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by IngridBoldt on 24.10.16.
 */
public class TestMessage {
    @Test
    public void testInitMessageConvert() throws Exception{
        String json = "{\"node\": 1, \"value\": 5.3, \"type\": 1, \"sequence_no\": 3}";
        JSONObject testInit = new JSONObject(json);
        Object te = Message.messageConvert(testInit);
        assertThat("InitMessage", te, instanceOf(InitMessage.class));
        System.out.println(te.toString());
    }

    @Test
    public void testProposeMessageConvert() throws Exception{
        String json = "{\"type\": "+ PROPOSE + ", \"sequence_no\": 3, \"node\": 1, " +
                "\"leader\": 2, \"proposal\": 3.5, \"value_store\": [" +
                "{\"node\": 2, \"value\": 0.4, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 1, \"value\": 0.6, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 3, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 4, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}" +
                "]}";
        JSONObject json_obj = new JSONObject(json);
        Object te = Message.messageConvert(json_obj);
        assertThat("ProposeMessage", te, instanceOf(ProposeMessage.class));
        System.out.println(te.toString());
    }

    @Test
    public void testPrevoteMessage() throws Exception{
        String json = "{\"node\": 1, \"value\": 5.3, \"type\": 3, \"leader\": 2, \"sequence_no\": 3}";
        JSONObject testPrev = new JSONObject(json);
        Object te = Message.messageConvert(testPrev);
        assertThat("PrevoteMessage", te, instanceOf(PrevoteMessage.class));
        System.out.println(te.toString());
    }

    @Test
    public void testVoteMessage() throws Exception{
        String json = "{\"node\": 1, \"value\": 5.3, \"type\": 4, \"leader\": 2, \"sequence_no\": 3}";
        JSONObject testVote = new JSONObject(json);
        Object te = Message.messageConvert(testVote);
        assertThat("VoteMessage", te, instanceOf(VoteMessage.class));
        System.out.println(te.toString());
    }

    @Test
    public void testVerifyProposal() throws Exception{
        String json = "{\"type\": "+ PROPOSE + ", \"sequence_no\": 3, \"node\": 1, " +
                "\"leader\": 2, \"proposal\": 3.5, \"value_store\": [" +
                "{\"node\": 2, \"value\": 0.4, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 1, \"value\": 0.6, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 3, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 4, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}" +
                "]}";
        JSONObject json_obj = new JSONObject(json);
        ProposeMessage te = (ProposeMessage) Message.messageConvert(json_obj);
        assertEquals("VerifyProposal", NormalCase.verifyProposal(te), false);
    }

    @Test
    public void testmedian() throws Exception{
        List<InitMessage> initStore = new LinkedList<>();
        InitMessage init1 = new InitMessage(1,1,0.4);
        InitMessage init2 = new InitMessage(1,2,0.3);
        InitMessage init3 = new InitMessage(1,3,0.3);
        InitMessage init4 = new InitMessage(1,4,0.5);
        initStore.add(init1);
        initStore.add(init2);
        initStore.add(init3);
        initStore.add(init4);
        assertEquals(0.4, Median.calculateMedian(initStore), 0);

    }

}
