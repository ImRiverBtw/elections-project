import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ElectionResultsView from "@/views/ElectionResultsView.vue";
import ElectionResultsMap from "@/components/ElectionResults/ElectionResultsMapComponent.vue";
import MajorityCalculator from  "@/components/ElectionResults/MajorityCalculatorComponent.vue";
import MunicipalityFinder from "@/components/ElectionResults/MunicipalityFinderComponent.vue";
import SeatDistribution from "@/components/ElectionResults/SeatDistributionComponent.vue";
import TotalTable from "@/components/ElectionResults/TotalTableComponent.vue";
import NewPostComponent from "@/components/Forum/NewPostComponent.vue";
import ForumView from "@/components/Forum/ForumView.vue";
import testView from "@/views/TestView.vue";
import accountListView from "@/views/AccountListView.vue";
import myProfileView from "@/views/MyProfileView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (About.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import('../views/AboutView.vue')
        },
        {
            path: '/parsetest',
            name: 'parsetest',
            component: () => import('../views/ParseTestView.vue')
        },
        {
            path: '/reset-password',
            name: 'reset-password',
            component: () => import('../components/ResetPasswordPopup.vue')        },
        {
            path: '/electionresults',
            name: 'electionresults',
            component: ElectionResultsView,
            children: [
                {
                    path: "resultsmap",
                    component: ElectionResultsMap
                },
                {
                    path: "majoritycalculator",
                    component: MajorityCalculator
                },
                {
                    path: "municipalityfinder",
                    component: MunicipalityFinder
                },
                {
                    path: "seatdistribution",
                    component: SeatDistribution
                },
                {
                    path: "totaltable",
                    component: TotalTable
                }
            ]
        },
        {
            path: '/forum',
            name: 'forum',
            component: ForumView,
            children: [
                {
                    path: "newpost",
                    component: NewPostComponent,
                },
            ]
        },
        {
            path: '/login',
            name: 'login',
            component: testView,
        },
        {
            path: '/accounts',
            name: 'ACCOUNTS',
            component: accountListView,
        },
        {
            path: '/profile',
            name: 'PROFILE',
            component: myProfileView
        }

    ]
})

export default router
