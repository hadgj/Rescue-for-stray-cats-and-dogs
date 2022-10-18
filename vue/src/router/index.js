import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manager.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager'),
    redirect:"/admin",
    children:[
      {path: 'edpassword',name:'edpassword',component:()=>import('../views/edpassword'),},
      {path: 'admin',name:'admin',component:()=>import('../views/admin'),},
      {path: 'news',name:'news',component:()=>import('../views/news'),},
      {path: 'adnews',name:'adnews',component:()=>import('../views/adnews'),} ,
      {path: 'pet',name:'pet',component:()=>import('../views/pet'),},
      {path: 'adpet',name:'adpet',component:()=>import('../views/adpet'),} ,
      {path: 'adopt',name:'adopt',component:()=>import('../views/adopt'),},
      {path: 'regular',name:'regular',component:()=>import('../views/regular'),} ,
      {path: 'organization',name:'organization',component:()=>import('../views/organization'),},
      {path: 'help',name:'help',component:()=>import('../views/help'),},
      {path: 'tousu',name:'tousu',component:()=>import('../views/tousu'),},
      {path: 'comment',name:'comment',component:()=>import('../views/comment'),},
      {path: 'chatbox',name:'chatbox',component:()=>import('../views/chatbox'),},
      {path: 'lanmu',name:'lanmu',component:()=>import('../views/lanmu'),},
      {path: 'zhulanmu',name:'zhulanmu',component:()=>import('../views/zhulanmu'),},
      {path: 'mingan',name:'mingan',component:()=>import('../views/mingan'),}
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
