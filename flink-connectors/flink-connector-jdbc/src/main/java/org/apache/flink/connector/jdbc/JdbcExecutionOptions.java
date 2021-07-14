/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.connector.jdbc;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.util.Preconditions;

import java.io.Serializable;
import java.util.Objects;

/**
 * JDBC sink batch options.
 */
@PublicEvolving
public class JdbcExecutionOptions implements Serializable {
	public static final int DEFAULT_MAX_RETRY_TIMES = 3;
	private static final int DEFAULT_INTERVAL_MILLIS = 0;
	public static final int DEFAULT_SIZE = 5000;
	public static final boolean DEFAULT_MLRECONNECTIONFLAG = false;


	private final long batchIntervalMs;
	private final int batchSize;
	private final int maxRetries;
	private boolean mlReconnectionFlag;


	private JdbcExecutionOptions(long batchIntervalMs, int batchSize, int maxRetries) {
		Preconditions.checkArgument(maxRetries >= 1);
		this.batchIntervalMs = batchIntervalMs;
		this.batchSize = batchSize;
		this.maxRetries = maxRetries;
	}

	private JdbcExecutionOptions(long batchIntervalMs, int batchSize, int maxRetries,boolean mlReconnectionFlagV) {
		Preconditions.checkArgument(maxRetries >= 1);
		this.batchIntervalMs = batchIntervalMs;
		this.batchSize = batchSize;
		this.maxRetries = maxRetries;
		this.mlReconnectionFlag=mlReconnectionFlagV;
	}

	public long getBatchIntervalMs() {
		return batchIntervalMs;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public boolean getMlReconnectionFlag() {
		return mlReconnectionFlag;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		JdbcExecutionOptions that = (JdbcExecutionOptions) o;
		return batchIntervalMs == that.batchIntervalMs &&
			batchSize == that.batchSize &&
			maxRetries == that.maxRetries &&
			mlReconnectionFlag==that.mlReconnectionFlag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(batchIntervalMs, batchSize, maxRetries,mlReconnectionFlag);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static JdbcExecutionOptions defaults() {
		return builder().build();
	}

	/**
	 * Builder for {@link JdbcExecutionOptions}.
	 */
	public static final class Builder {
		private long intervalMs = DEFAULT_INTERVAL_MILLIS;
		private int size = DEFAULT_SIZE;
		private int maxRetries = DEFAULT_MAX_RETRY_TIMES;
		private boolean mlReconnectionFlagV=DEFAULT_MLRECONNECTIONFLAG;

		public Builder withBatchSize(int size) {
			this.size = size;
			return this;
		}

		public Builder withMlReconnectionFlag(boolean mlReconnectionFlag) {
			this.mlReconnectionFlagV = mlReconnectionFlag;
			return this;
		}

		public Builder withBatchIntervalMs(long intervalMs) {
			this.intervalMs = intervalMs;
			return this;
		}

		public Builder withMaxRetries(int maxRetries) {
			this.maxRetries = maxRetries;
			return this;
		}

		public JdbcExecutionOptions build() {
			return new JdbcExecutionOptions(intervalMs, size, maxRetries,mlReconnectionFlagV);
		}
	}
}
