/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.manager.verification.runtime;

import java.util.List;

import org.streampipes.manager.verification.messages.VerificationResult;
import org.streampipes.manager.verification.structure.Verifier;
import org.streampipes.model.graph.DataSourceDescription;

public class SourceSchemaVerifier implements Verifier {

	private DataSourceDescription sep;
	
	public SourceSchemaVerifier(DataSourceDescription sep) {
		this.sep = sep;
	}
	
	@Override
	public List<VerificationResult> validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
