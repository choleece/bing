export const formatNumber = n => {
    n = n.toString();
    return n[1] ? n : '0' + n;
};

/**
 * 日期增加几天
 * @param date
 * @param days
 * @returns {string}
 */
export const datePlusDays = (date, days) => {
    date.setDate(date.getDate() + days);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return [year, month, day].map(formatNumber).join('-');
};

export const getDate = date => {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return [year, month, day].map(formatNumber).join('-');
};
