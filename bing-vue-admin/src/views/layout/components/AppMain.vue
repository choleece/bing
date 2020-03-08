<template>
  <section class="app-main">
    <Breadcrumb></Breadcrumb>
    <transition name="fade-transform" mode="out-in">
      <!-- or name="fade" -->
      <!-- <router-view :key="key"></router-view> -->
      <router-view/>
    </transition>
  </section>
</template>

<script>
    import Breadcrumb from '@/components/Breadcrumb'

export default {
  name: 'AppMain',
  computed: {
    // key() {
    //   return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
    // }
  },
  components:{
     Breadcrumb
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  position: relative;
  overflow: hidden;
  background-color: #f5f5f5;
  padding: 0 20px;
  flex: 1;
  width: 100%;
}
</style>
