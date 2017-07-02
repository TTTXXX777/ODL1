/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 * Copyright (c) 2015 Brocade Communications Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.cluster.raft;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import akka.japi.Procedure;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.cluster.raft.MockRaftActorContext.MockPayload;
import org.opendaylight.controller.cluster.raft.MockRaftActorContext.MockReplicatedLogEntry;

/**
*
*/
public class AbstractReplicatedLogImplTest {

    private MockAbstractReplicatedLogImpl replicatedLogImpl;

    @Before
    public void setUp() {
        replicatedLogImpl = new MockAbstractReplicatedLogImpl();
        // create a set of initial entries in the in-memory log
        replicatedLogImpl.append(new MockReplicatedLogEntry(1, 0, new MockPayload("A")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(1, 1, new MockPayload("B")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(1, 2, new MockPayload("C")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 3, new MockPayload("D")));

    }

    @Test
    public void testEmptyLog() {
        replicatedLogImpl = new MockAbstractReplicatedLogImpl();

        assertEquals("size", 0, replicatedLogImpl.size());
        assertEquals("dataSize", 0, replicatedLogImpl.dataSize());
        assertEquals("getSnapshotIndex", -1, replicatedLogImpl.getSnapshotIndex());
        assertEquals("getSnapshotTerm", -1, replicatedLogImpl.getSnapshotTerm());
        assertEquals("lastIndex", -1, replicatedLogImpl.lastIndex());
        assertEquals("lastTerm", -1, replicatedLogImpl.lastTerm());
        assertEquals("isPresent", false, replicatedLogImpl.isPresent(0));
        assertEquals("isInSnapshot", false, replicatedLogImpl.isInSnapshot(0));
        Assert.assertNull("get(0)", replicatedLogImpl.get(0));
        Assert.assertNull("last", replicatedLogImpl.last());

        List<ReplicatedLogEntry> list = replicatedLogImpl.getFrom(0, 1, ReplicatedLog.NO_MAX_SIZE);
        assertEquals("getFrom size", 0, list.size());

        assertEquals("removeFrom", -1, replicatedLogImpl.removeFrom(1));

        replicatedLogImpl.setSnapshotIndex(2);
        replicatedLogImpl.setSnapshotTerm(1);

        assertEquals("getSnapshotIndex", 2, replicatedLogImpl.getSnapshotIndex());
        assertEquals("getSnapshotTerm", 1, replicatedLogImpl.getSnapshotTerm());
        assertEquals("lastIndex", 2, replicatedLogImpl.lastIndex());
        assertEquals("lastTerm", 1, replicatedLogImpl.lastTerm());
    }

    @Test
    public void testIndexOperations() {

        // check if the values returned are correct, with snapshotIndex = -1
        assertEquals("B", replicatedLogImpl.get(1).getData().toString());
        assertEquals("D", replicatedLogImpl.last().getData().toString());
        assertEquals(3, replicatedLogImpl.lastIndex());
        assertEquals(2, replicatedLogImpl.lastTerm());
        assertEquals(2, replicatedLogImpl.getFrom(2).size());
        assertEquals(4, replicatedLogImpl.size());
        assertTrue(replicatedLogImpl.isPresent(2));
        assertFalse(replicatedLogImpl.isPresent(4));
        assertFalse(replicatedLogImpl.isInSnapshot(2));

        // now create a snapshot of 3 entries, with 1 unapplied entry left in the log
        // It removes the entries which have made it to snapshot
        // and updates the snapshot index and term
        takeSnapshot(3);

        // check the values after the snapshot.
        // each index value passed in the test is the logical index (log entry index)
        // which gets mapped to the list's physical index
        assertEquals("D", replicatedLogImpl.get(3).getData().toString());
        assertEquals("D", replicatedLogImpl.last().getData().toString());
        assertNull(replicatedLogImpl.get(1));
        assertEquals(3, replicatedLogImpl.lastIndex());
        assertEquals(2, replicatedLogImpl.lastTerm());
        assertEquals(0, replicatedLogImpl.getFrom(2).size());
        assertEquals(1, replicatedLogImpl.size());
        assertFalse(replicatedLogImpl.isPresent(2));
        assertTrue(replicatedLogImpl.isPresent(3));
        assertFalse(replicatedLogImpl.isPresent(4));
        assertTrue(replicatedLogImpl.isInSnapshot(2));

        // append few more entries
        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 4, new MockPayload("E")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 5, new MockPayload("F")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(3, 6, new MockPayload("G")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(3, 7, new MockPayload("H")));

        // check their values as well
        assertEquals(5, replicatedLogImpl.size());
        assertEquals("D", replicatedLogImpl.get(3).getData().toString());
        assertEquals("E", replicatedLogImpl.get(4).getData().toString());
        assertEquals("H", replicatedLogImpl.last().getData().toString());
        assertEquals(3, replicatedLogImpl.lastTerm());
        assertEquals(7, replicatedLogImpl.lastIndex());
        assertTrue(replicatedLogImpl.isPresent(7));
        assertFalse(replicatedLogImpl.isInSnapshot(7));
        assertEquals(1, replicatedLogImpl.getFrom(7).size());
        assertEquals(2, replicatedLogImpl.getFrom(6).size());

        // take a second snapshot with 5 entries with 0 unapplied entries left in the log
        takeSnapshot(5);

        assertEquals(0, replicatedLogImpl.size());
        assertNull(replicatedLogImpl.last());
        assertNull(replicatedLogImpl.get(7));
        assertNull(replicatedLogImpl.get(1));
        assertFalse(replicatedLogImpl.isPresent(7));
        assertTrue(replicatedLogImpl.isInSnapshot(7));
        assertEquals(0, replicatedLogImpl.getFrom(7).size());
        assertEquals(0, replicatedLogImpl.getFrom(6).size());

    }

    @Test
    public void testGetFromWithMax() {
        List<ReplicatedLogEntry> from = replicatedLogImpl.getFrom(0, 1, ReplicatedLog.NO_MAX_SIZE);
        Assert.assertEquals(1, from.size());
        Assert.assertEquals("A", from.get(0).getData().toString());

        from = replicatedLogImpl.getFrom(0, 20, ReplicatedLog.NO_MAX_SIZE);
        Assert.assertEquals(4, from.size());
        Assert.assertEquals("A", from.get(0).getData().toString());
        Assert.assertEquals("D", from.get(3).getData().toString());

        from = replicatedLogImpl.getFrom(1, 2, ReplicatedLog.NO_MAX_SIZE);
        Assert.assertEquals(2, from.size());
        Assert.assertEquals("B", from.get(0).getData().toString());
        Assert.assertEquals("C", from.get(1).getData().toString());

        from = replicatedLogImpl.getFrom(1, 3, 2);
        Assert.assertEquals(2, from.size());
        Assert.assertEquals("B", from.get(0).getData().toString());
        Assert.assertEquals("C", from.get(1).getData().toString());

        from = replicatedLogImpl.getFrom(1, 3, 3);
        Assert.assertEquals(3, from.size());
        Assert.assertEquals("B", from.get(0).getData().toString());
        Assert.assertEquals("C", from.get(1).getData().toString());
        Assert.assertEquals("D", from.get(2).getData().toString());

        from = replicatedLogImpl.getFrom(1, 2, 3);
        Assert.assertEquals(2, from.size());
        Assert.assertEquals("B", from.get(0).getData().toString());
        Assert.assertEquals("C", from.get(1).getData().toString());

        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 4, new MockPayload("12345")));
        from = replicatedLogImpl.getFrom(4, 2, 2);
        Assert.assertEquals(1, from.size());
        Assert.assertEquals("12345", from.get(0).getData().toString());
    }

    @Test
    public void testSnapshotPreCommit() {
        //add 4 more entries
        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 4, new MockPayload("E")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 5, new MockPayload("F")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(3, 6, new MockPayload("G")));
        replicatedLogImpl.append(new MockReplicatedLogEntry(3, 7, new MockPayload("H")));

        //sending negative values should not cause any changes
        replicatedLogImpl.snapshotPreCommit(-1, -1);
        assertEquals(8, replicatedLogImpl.size());
        assertEquals(-1, replicatedLogImpl.getSnapshotIndex());
        assertEquals(-1, replicatedLogImpl.getSnapshotTerm());

        replicatedLogImpl.snapshotPreCommit(4, 2);
        assertEquals(3, replicatedLogImpl.size());
        assertEquals(4, replicatedLogImpl.getSnapshotIndex());
        assertEquals(2, replicatedLogImpl.getSnapshotTerm());

        replicatedLogImpl.snapshotPreCommit(6, 3);
        assertEquals(1, replicatedLogImpl.size());
        assertEquals(6, replicatedLogImpl.getSnapshotIndex());
        assertEquals(3, replicatedLogImpl.getSnapshotTerm());

        replicatedLogImpl.snapshotPreCommit(7, 3);
        assertEquals(0, replicatedLogImpl.size());
        assertEquals(7, replicatedLogImpl.getSnapshotIndex());
        assertEquals(3, replicatedLogImpl.getSnapshotTerm());

        //running it again on an empty list should not throw exception
        replicatedLogImpl.snapshotPreCommit(7, 3);
        assertEquals(0, replicatedLogImpl.size());
        assertEquals(7, replicatedLogImpl.getSnapshotIndex());
        assertEquals(3, replicatedLogImpl.getSnapshotTerm());
    }

    @Test
    public void testSnapshotCommit() {

        replicatedLogImpl.snapshotPreCommit(1, 1);

        replicatedLogImpl.snapshotCommit();

        assertEquals("size", 2, replicatedLogImpl.size());
        assertEquals("dataSize", 2, replicatedLogImpl.dataSize());
        assertEquals("getSnapshotIndex", 1, replicatedLogImpl.getSnapshotIndex());
        assertEquals("getSnapshotTerm", 1, replicatedLogImpl.getSnapshotTerm());
        assertEquals("lastIndex", 3, replicatedLogImpl.lastIndex());
        assertEquals("lastTerm", 2, replicatedLogImpl.lastTerm());

        Assert.assertNull("get(0)", replicatedLogImpl.get(0));
        Assert.assertNull("get(1)", replicatedLogImpl.get(1));
        Assert.assertNotNull("get(2)", replicatedLogImpl.get(2));
        Assert.assertNotNull("get(3)", replicatedLogImpl.get(3));
    }

    @Test
    public void testSnapshotRollback() {

        replicatedLogImpl.snapshotPreCommit(1, 1);

        assertEquals("size", 2, replicatedLogImpl.size());
        assertEquals("getSnapshotIndex", 1, replicatedLogImpl.getSnapshotIndex());
        assertEquals("getSnapshotTerm", 1, replicatedLogImpl.getSnapshotTerm());

        replicatedLogImpl.snapshotRollback();

        assertEquals("size", 4, replicatedLogImpl.size());
        assertEquals("dataSize", 4, replicatedLogImpl.dataSize());
        assertEquals("getSnapshotIndex", -1, replicatedLogImpl.getSnapshotIndex());
        assertEquals("getSnapshotTerm", -1, replicatedLogImpl.getSnapshotTerm());
        Assert.assertNotNull("get(0)", replicatedLogImpl.get(0));
        Assert.assertNotNull("get(3)", replicatedLogImpl.get(3));
    }

    @Test
    public void testIsPresent() {
        assertTrue(replicatedLogImpl.isPresent(0));
        assertTrue(replicatedLogImpl.isPresent(1));
        assertTrue(replicatedLogImpl.isPresent(2));
        assertTrue(replicatedLogImpl.isPresent(3));

        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 4, new MockPayload("D")));
        replicatedLogImpl.snapshotPreCommit(3, 2); //snapshot on 3
        replicatedLogImpl.snapshotCommit();

        assertFalse(replicatedLogImpl.isPresent(0));
        assertFalse(replicatedLogImpl.isPresent(1));
        assertFalse(replicatedLogImpl.isPresent(2));
        assertFalse(replicatedLogImpl.isPresent(3));
        assertTrue(replicatedLogImpl.isPresent(4));

        replicatedLogImpl.snapshotPreCommit(4, 2); //snapshot on 4
        replicatedLogImpl.snapshotCommit();
        assertFalse(replicatedLogImpl.isPresent(4));

        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 5, new MockPayload("D")));
        assertTrue(replicatedLogImpl.isPresent(5));
    }

    @Test
    public void testRemoveFrom() {

        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 4, new MockPayload("E", 2)));
        replicatedLogImpl.append(new MockReplicatedLogEntry(2, 5, new MockPayload("F", 3)));

        assertEquals("dataSize", 9, replicatedLogImpl.dataSize());

        long adjusted = replicatedLogImpl.removeFrom(4);
        assertEquals("removeFrom - adjusted", 4, adjusted);
        assertEquals("size", 4, replicatedLogImpl.size());
        assertEquals("dataSize", 4, replicatedLogImpl.dataSize());

        takeSnapshot(1);

        adjusted = replicatedLogImpl.removeFrom(2);
        assertEquals("removeFrom - adjusted", 1, adjusted);
        assertEquals("size", 1, replicatedLogImpl.size());
        assertEquals("dataSize", 1, replicatedLogImpl.dataSize());

        assertEquals("removeFrom - adjusted", -1, replicatedLogImpl.removeFrom(0));
        assertEquals("removeFrom - adjusted", -1, replicatedLogImpl.removeFrom(100));
    }

    // create a snapshot for test
    public Map<Long, String> takeSnapshot(final int numEntries) {
        Map<Long, String> map = new HashMap<>(numEntries);

        long lastIndex = 0;
        long lastTerm = 0;
        for(int i = 0; i < numEntries; i++) {
            ReplicatedLogEntry entry = replicatedLogImpl.getAtPhysicalIndex(i);
            map.put(entry.getIndex(), entry.getData().toString());
            lastIndex = entry.getIndex();
            lastTerm = entry.getTerm();
        }

        replicatedLogImpl.snapshotPreCommit(lastIndex, lastTerm);
        replicatedLogImpl.snapshotCommit();

        return map;

    }
    class MockAbstractReplicatedLogImpl extends AbstractReplicatedLogImpl {
        @Override
        public void appendAndPersist(final ReplicatedLogEntry replicatedLogEntry) {
        }

        @Override
        public boolean removeFromAndPersist(final long index) {
            return true;
        }

        @Override
        public void appendAndPersist(ReplicatedLogEntry replicatedLogEntry, Procedure<ReplicatedLogEntry> callback) {
        }

        @Override
        public void captureSnapshotIfReady(ReplicatedLogEntry replicatedLogEntry) {
        }
    }
}
