/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.apache.drill.exec.store.json;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.apache.drill.common.exceptions.ExecutionSetupException;
import org.apache.drill.exec.ops.FragmentContext;
import org.apache.drill.exec.physical.impl.BatchCreator;
import org.apache.drill.exec.physical.impl.ScanBatch;
import org.apache.drill.exec.record.RecordBatch;
import org.apache.drill.exec.store.RecordReader;

import java.util.List;

public class JSONScanBatchCreator implements BatchCreator<JSONSubScan> {

    @Override
    public RecordBatch getBatch(FragmentContext context, JSONSubScan config, List<RecordBatch> children) throws ExecutionSetupException {
        Preconditions.checkArgument(children.isEmpty());
        List<JSONGroupScan.ScanEntry> entries = config.getReadEntries();
        List<RecordReader> readers = Lists.newArrayList();
        for (JSONGroupScan.ScanEntry e : entries) {
            readers.add(new JSONRecordReader(context, e.getUrl()));
        }

        return new ScanBatch(context, readers.iterator());
    }
}
