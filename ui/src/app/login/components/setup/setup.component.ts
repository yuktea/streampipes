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
 *
 */

import {Component} from "@angular/core";
import {RestApi} from "../../../services/rest-api.service";
import {FormGroup} from "@angular/forms";
import {LoginService} from "../../services/login.service";

@Component({
    selector: 'setup',
    templateUrl: './setup.component.html'
})
export class SetupComponent {

    installationFinished: any;
    installationSuccessful: any;
    installationResults: any;
    loading: any;
    showAdvancedSettings: any;
    setup: any = {
        couchDbHost: '',
        kafkaHost: '',
        zookeeperHost: '',
        jmsHost: '',
        adminEmail: '',
        adminPassword: '',
        installPipelineElements: true
    };
    setupForm: any;
    installationRunning: any;
    nextTaskTitle: any;

    parentForm: FormGroup = new FormGroup({});

    constructor(private loginService: LoginService,
                private RestApi: RestApi) {

        this.installationFinished = false;
        this.installationSuccessful = false;
        this.installationResults = [];
        this.loading = false;
        this.showAdvancedSettings = false;

        this.setup
    }

    configure(currentInstallationStep) {
        this.installationRunning = true;
        this.loading = true;
        this.loginService.setupInstall(this.setup, currentInstallationStep).subscribe(data => {
            this.installationResults = this.installationResults.concat(data.statusMessages);
            this.nextTaskTitle = data.nextTaskTitle;
            let nextInstallationStep = currentInstallationStep + 1;
            if (nextInstallationStep > (data.installationStepCount - 1)) {
                this.RestApi.configured()
                    .subscribe(data => {
                        if (data.configured) {
                            this.installationFinished = true;
                            this.loading = false;
                        }
                    }), (data => {
                    this.loading = false;
                    this.showToast("Fatal error, contact administrator");
                });
            } else {
                this.configure(nextInstallationStep);
            }
        });
    }

    showToast(string) {
        // this.$mdToast.show(
        //     this.$mdToast.simple()
        //         .content(string)
        //         .position("right")
        //         .hideDelay(3000)
        // );
    };

    addPod(podUrls) {
        if (podUrls == undefined) podUrls = [];
        podUrls.push("localhost");
    }

    removePod(podUrls, index) {
        podUrls.splice(index, 1);
    }
}
