import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadChildren:() => import('./components/schedule-overview/routes').then(m => m.SCHEDULE_OVERVIEW_ROUTES)
    }
];
