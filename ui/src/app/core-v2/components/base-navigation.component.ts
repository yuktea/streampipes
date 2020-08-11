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



import {Router} from "@angular/router";

export abstract class BaseNavigationComponent {

  activePageName: string;
  activePage: any;

  authenticated: boolean = true;

  public menu = [
    {
      link: '',
      title: 'Home',
      icon: 'home'
    },
    {
      link: 'editor',
      title: 'Editor',
      icon: 'dashboard'
    },
    {
      link: 'pipelines',
      title: 'Pipelines',
      icon: 'play_arrow'
    },
    {
      link: 'connect',
      title: 'StreamPipes Connect',
      icon: 'power'
    },
    {
      link: 'dashboard',
      title: 'Dashboard',
      icon: 'insert_chart'
    },
    {
      link: 'dataexplorer',
      title: 'Data Explorer',
      icon: 'search'
    },
    {
      link: 'app-overview',
      title: 'Apps',
      icon: 'apps'
    },
  ];

  admin = [
    {
      link: 'add',
      title: 'Install Pipeline Elements',
      icon: 'cloud_download'
    },
    {
      link: 'configuration',
      title: 'Configuration',
      icon: 'settings'
    },
  ];

  constructor(protected Router: Router) {

  }

  getActivePage() {
    return this.activePage;
  }

  getPageTitle(path) {
    var allMenuItems = this.menu.concat(this.admin);
    var currentTitle = 'StreamPipes';
    allMenuItems.forEach(m => {
      if (m.link === path) {
        currentTitle = m.title;
      }
    });
    if (path == 'streampipes.pipelineDetails') {
      currentTitle = 'Pipeline Details';
    } else if (path == 'streampipes.edit') {
      currentTitle = this.menu[0].title;
    }
    return currentTitle;
  }

  go(path, payload?) {
    if (payload === undefined) {
      this.Router.navigateByUrl(path);
      this.activePage = path;
    } else {
      this.Router.navigateByUrl(path, payload);
      this.activePage = path;
    }
    this.activePageName = this.getPageTitle(this.activePage);
  };

}