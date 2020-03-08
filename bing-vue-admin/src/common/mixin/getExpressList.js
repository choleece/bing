module.exports = {
    data() {
        return {
            expressList:[]
        }
    },
    mounted() {
        this.getExpressList()
    },
    methods: {
        getExpressList() {
            this.service({
                url:this.API.expressLst,
                method: "get",
            }).then(res => {
                if (res.success && res.data) {
                    this.expressList = res.data
                }
            })
        },
    }
}
