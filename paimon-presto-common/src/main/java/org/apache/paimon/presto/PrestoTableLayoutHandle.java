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

package org.apache.paimon.presto;

import org.apache.paimon.shade.guava30.com.google.common.base.MoreObjects;

import com.facebook.presto.common.predicate.TupleDomain;
import com.facebook.presto.spi.ColumnHandle;
import com.facebook.presto.spi.ConnectorTableLayoutHandle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Presto {@link ConnectorTableLayoutHandle}. */
public class PrestoTableLayoutHandle implements ConnectorTableLayoutHandle {

    private final PrestoTableHandle tableHandle;
    private final TupleDomain<ColumnHandle> constraintSummary;
    private final Map<String, PrestoColumnHandle> predicateColumns;
    private final List<PrestoColumnHandle> partitionColumns;

	@JsonCreator
    public PrestoTableLayoutHandle(
            @JsonProperty("tableHandle") PrestoTableHandle tableHandle,
            @JsonProperty("constraintSummary") TupleDomain<ColumnHandle> constraintSummary,
            @JsonProperty("predicateColumns") Map<String, PrestoColumnHandle> predicateColumns,
            @JsonProperty("partitionColumns") List<PrestoColumnHandle> partitionColumns) {
        this.tableHandle = Objects.requireNonNull(tableHandle, "table is null");
        this.constraintSummary = constraintSummary;
        this.predicateColumns = predicateColumns;
        this.partitionColumns = partitionColumns;
    }

    public Map<String, PrestoColumnHandle> getPredicateColumns() {
		return predicateColumns;
	}

	public List<PrestoColumnHandle> getPartitionColumns() {
		return partitionColumns;
	}

    @JsonProperty
    public PrestoTableHandle getTableHandle() {
        return tableHandle;
    }

    @JsonProperty
    public TupleDomain<ColumnHandle> getConstraintSummary() {
        return constraintSummary;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        PrestoTableLayoutHandle other = (PrestoTableLayoutHandle) obj;
        return Objects.equals(tableHandle, other.tableHandle)
                && Objects.equals(constraintSummary, other.constraintSummary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHandle, constraintSummary);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tableHandle", tableHandle)
                .add("constraintSummary", constraintSummary)
                .toString();
    }
}
