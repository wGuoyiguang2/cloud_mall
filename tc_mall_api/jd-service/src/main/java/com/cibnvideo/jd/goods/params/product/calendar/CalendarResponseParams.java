package com.cibnvideo.jd.goods.params.product.calendar;

import com.cibnvideo.jd.common.params.BaseResponseParams;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 21:52
 */
public class CalendarResponseParams extends BaseResponseParams {
    private CalendarRepoVo result;

    public CalendarRepoVo getResult() {
        return result;
    }

    public void setResult(CalendarRepoVo result) {
        this.result = result;
    }

    class CalendarRepoVo {
        private SkuClassifyResult skuClassifyResult;
        private CalendarListResult calendarListResult;
        private LaCalendarListResult laCalendarListResult;

        public SkuClassifyResult getSkuClassifyResult() {
            return skuClassifyResult;
        }

        public void setSkuClassifyResult(SkuClassifyResult skuClassifyResult) {
            this.skuClassifyResult = skuClassifyResult;
        }

        public CalendarListResult getCalendarListResult() {
            return calendarListResult;
        }

        public void setCalendarListResult(CalendarListResult calendarListResult) {
            this.calendarListResult = calendarListResult;
        }

        public LaCalendarListResult getLaCalendarListResult() {
            return laCalendarListResult;
        }

        public void setLaCalendarListResult(LaCalendarListResult laCalendarListResult) {
            this.laCalendarListResult = laCalendarListResult;
        }

        class SkuClassifyResult {
            private String resultCode;
            private String resultMessage;
            private Map<String, String> skuClassifyMaps;

            public String getResultCode() {
                return resultCode;
            }

            public void setResultCode(String resultCode) {
                this.resultCode = resultCode;
            }

            public String getResultMessage() {
                return resultMessage;
            }

            public void setResultMessage(String resultMessage) {
                this.resultMessage = resultMessage;
            }

            public Map<String, String> getSkuClassifyMaps() {
                return skuClassifyMaps;
            }

            public void setSkuClassifyMaps(Map<String, String> skuClassifyMaps) {
                this.skuClassifyMaps = skuClassifyMaps;
            }
        }
    }

    class CalendarListResult {

        private String resultCode;
        private String resultMessage;
        private Map<String, String> promiseTime;
        private String tipMsg;
        private List<CalendarVo> calendarList;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }

        public Map<String, String> getPromiseTime() {
            return promiseTime;
        }

        public void setPromiseTime(Map<String, String> promiseTime) {
            this.promiseTime = promiseTime;
        }

        public String getTipMsg() {
            return tipMsg;
        }

        public void setTipMsg(String tipMsg) {
            this.tipMsg = tipMsg;
        }

        public List<CalendarVo> getCalendarList() {
            return calendarList;
        }

        public void setCalendarList(List<CalendarVo> calendarList) {
            this.calendarList = calendarList;
        }

        class CalendarVo {
            private String dateStr;
            private String week;
            private Boolean today;
            private List<TimeVo> timeList;

            public String getDateStr() {
                return dateStr;
            }

            public void setDateStr(String dateStr) {
                this.dateStr = dateStr;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public Boolean getToday() {
                return today;
            }

            public void setToday(Boolean today) {
                this.today = today;
            }

            public List<TimeVo> getTimeList() {
                return timeList;
            }

            public void setTimeList(List<TimeVo> timeList) {
                this.timeList = timeList;
            }

            class TimeVo {
                private Map<String, String> sendpay;
                private String timeRange;
                private Boolean enable;
                private Boolean selected;
                private Boolean timeRangeCode;

                public Map<String, String> getSendpay() {
                    return sendpay;
                }

                public void setSendpay(Map<String, String> sendpay) {
                    this.sendpay = sendpay;
                }

                public String getTimeRange() {
                    return timeRange;
                }

                public void setTimeRange(String timeRange) {
                    this.timeRange = timeRange;
                }

                public Boolean getEnable() {
                    return enable;
                }

                public void setEnable(Boolean enable) {
                    this.enable = enable;
                }

                public Boolean getSelected() {
                    return selected;
                }

                public void setSelected(Boolean selected) {
                    this.selected = selected;
                }

                public Boolean getTimeRangeCode() {
                    return timeRangeCode;
                }

                public void setTimeRangeCode(Boolean timeRangeCode) {
                    this.timeRangeCode = timeRangeCode;
                }
            }
        }
    }

    class LaCalendarListResult {

        private String resultCode;
        private String resultMessage;
        private List<String> reservingDateList;
        private Map<String, String> reservingInstallDateMap;
        private Integer defaultDate;
        private Boolean supportInstall;
        private Boolean supportNightShip;
        private Boolean supportShip;
        private Map<String, String> sendpay;
        private List<String> skuInfoList;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }

        public List<String> getReservingDateList() {
            return reservingDateList;
        }

        public void setReservingDateList(List<String> reservingDateList) {
            this.reservingDateList = reservingDateList;
        }

        public Map<String, String> getReservingInstallDateMap() {
            return reservingInstallDateMap;
        }

        public void setReservingInstallDateMap(Map<String, String> reservingInstallDateMap) {
            this.reservingInstallDateMap = reservingInstallDateMap;
        }

        public Integer getDefaultDate() {
            return defaultDate;
        }

        public void setDefaultDate(Integer defaultDate) {
            this.defaultDate = defaultDate;
        }

        public Boolean getSupportInstall() {
            return supportInstall;
        }

        public void setSupportInstall(Boolean supportInstall) {
            this.supportInstall = supportInstall;
        }

        public Boolean getSupportNightShip() {
            return supportNightShip;
        }

        public void setSupportNightShip(Boolean supportNightShip) {
            this.supportNightShip = supportNightShip;
        }

        public Boolean getSupportShip() {
            return supportShip;
        }

        public void setSupportShip(Boolean supportShip) {
            this.supportShip = supportShip;
        }

        public Map<String, String> getSendpay() {
            return sendpay;
        }

        public void setSendpay(Map<String, String> sendpay) {
            this.sendpay = sendpay;
        }

        public List<String> getSkuInfoList() {
            return skuInfoList;
        }

        public void setSkuInfoList(List<String> skuInfoList) {
            this.skuInfoList = skuInfoList;
        }
    }
}
