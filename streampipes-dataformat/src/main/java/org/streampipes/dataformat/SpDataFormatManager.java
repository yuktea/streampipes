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

package org.streampipes.dataformat;

import org.streampipes.model.grounding.TransportFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum SpDataFormatManager {

  INSTANCE;

  private List<SpDataFormatFactory> availableDataFormats;

  SpDataFormatManager() {
    this.availableDataFormats = new ArrayList<>();
  }

  public void register(SpDataFormatFactory dataFormatDefinition) {
    availableDataFormats.add(dataFormatDefinition);
  }

  public List<SpDataFormatFactory> getAvailableDataFormats() {
    return availableDataFormats;
  }

  public Optional<SpDataFormatDefinition> findDefinition(TransportFormat transportFormat) {
    // TODO why is transportFormat.getRdfType a list?
    return this.availableDataFormats
            .stream()
            .filter
                    (adf -> transportFormat
                            .getRdfType()
                            .stream()
                            .anyMatch(tf -> tf.toString().equals(adf
                                    .getTransportFormatRdfUri())))
            .map(SpDataFormatFactory::createInstance)
            .findFirst();

  }

}
