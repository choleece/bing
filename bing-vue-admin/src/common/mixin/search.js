module.exports = {
    data() {
        return {
            tableHeight: 0
        }
    },
    mounted() {
        this.getTableHeight()
        let $this = this
        window.onresize = () => {
            $this.getTableHeight()
        }
    },
    methods: {
        getTableHeight() {
            this.$nextTick(() => {
                let el = document.querySelector(".main-table");
                this.tableHeight = el.offsetHeight
                console.log(this.tableHeight);
            })
        }
    }
}
