/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.runtime.typeutils;

import org.apache.flink.api.common.typeutils.SerializerTestBase;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.data.binary.BinaryRowData;
import org.apache.flink.table.data.writer.BinaryRowWriter;
import org.apache.flink.table.runtime.util.WindowKey;

/** A test for the {@link WindowKeySerializer}. */
public class WindowKeySerializerTest extends SerializerTestBase<WindowKey> {

    @Override
    protected TypeSerializer<WindowKey> createSerializer() {
        return new WindowKeySerializer((AbstractRowDataSerializer) new BinaryRowDataSerializer(2));
    }

    @Override
    protected int getLength() {
        return -1;
    }

    @Override
    protected Class<WindowKey> getTypeClass() {
        return WindowKey.class;
    }

    @Override
    protected WindowKey[] getTestData() {
        return new WindowKey[] {
            new WindowKey(1000L, createRow("11", 1)),
            new WindowKey(-2000L, createRow("12", 2)),
            new WindowKey(Long.MAX_VALUE, createRow("132", 3)),
            new WindowKey(Long.MIN_VALUE, createRow("55", 4)),
        };
    }

    private static BinaryRowData createRow(String f0, int f1) {
        BinaryRowData row = new BinaryRowData(2);
        BinaryRowWriter writer = new BinaryRowWriter(row);
        writer.writeString(0, StringData.fromString(f0));
        writer.writeInt(1, f1);
        writer.complete();
        return row;
    }
}
