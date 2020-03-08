module.exports = {
    data() {
        return {
            options4: []
        }
    },
    methods: {
        remoteMethod(query) {
            let str = ""
            if (query.target) {
                str = query.target.value;
            } else {
                str = query
            }
            this.searchLoading = true;
            this.service({
                url: this.API.terminalList,
                method: "post",
                params: {
                    terminalName: str,
                },

            }).then(res => {
                if (res.success) {
                    this.options4 = res.data;
                }
            }).catch(() => {
            })
        },
    }
}
