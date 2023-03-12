package com.example.simplework.utils;

import com.example.simplework.constant.ResponseStatusCodeEnum;
import com.example.simplework.exception.BusinessException;
import com.example.simplework.factory.response.GeneralResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@SuppressWarnings("squid:S1104")
public class Common {
    @Autowired
    private ObjectMapper objectMapper;

    public static boolean checkConsecutive(String str) {

        String[] array = str.split("");
        int size = array.length;

        int[] array1 = new int[size];
        for (int i = 0; i < size; i++) {
            array1[i] = Integer.parseInt(array[i]);
        }

        boolean result = true;
        for (int i = 0; i < size - 1; i++) {
            if ((array1[i + 1] - array1[i]) != 1) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean checkSameDigits(String str) {
        char[] s = str.toCharArray();
        int l = s.length;
        for (int i = 1; i < l; i++) {
            if (s[i] - s[i - 1] != 0) {
                return false;
            }
        }
        return true;
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static <T> T jsonToObject(String jsonData, Class<T> classOutput) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonData, classOutput);
        } catch (Exception ex) {
            return jsonToObjectFronGson(jsonData, classOutput);
        }
    }

    private static <T> T jsonToObjectFronGson(String jsonData, Class<T> classOutput) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonData, classOutput);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String gsonToJson(Object classInput) {
        try {
            Gson gsons = new Gson();
            return gsons.toJson(classInput);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public List<String> stringToList(String input, String separator) {
        return Arrays.asList(StringUtils.split(input, separator));
    }

    public List<String> stringToList(String input) {
        return stringToList(input, ";");
    }

    public URIBuilder getUriBuilder(String host, String endpoint) {
        try {
            return new URIBuilder(host).setPath(endpoint);
        } catch (URISyntaxException e) {
            log.error("Wrong URIBuilder {} {}", host, endpoint);
            throw new BusinessException(ResponseStatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public String standardString(String input) {
        return input.replaceAll("\\s+", "");
    }

    public String removeSpecialChar(String input) {
        return input.replaceAll("[+^']", "").replace("\\", "");
    }

    public String standardCustomerName(String input) {
        return input.replaceAll("[+^-]", "").replaceAll("\\s+", " ");
    }

    public URIBuilder getUriBuilder(String host) {
        try {
            return new URIBuilder(host);
        } catch (URISyntaxException e) {
            log.error("Wrong URIBuilder {}", host);
            throw new BusinessException(ResponseStatusCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("squid:S2259")
    public <T> void verifyResponse(ResponseEntity<GeneralResponse<T>> res) {
        if (res.getBody() == null
                || res.getBody().getStatus() == null
                || !"00".equals(res.getBody().getStatus().getCode())) {
            throw new NullPointerException();
        }
    }

    @SuppressWarnings("squid:S2259")
    public <T> void verifyResponse(ResponseEntity<GeneralResponse<T>> res, boolean checkStatusCode) {
        if (res.getBody() == null
                || res.getBody().getStatus() == null
                || (checkStatusCode && !"00".equals(res.getBody().getStatus().getCode()))) {
            throw new NullPointerException();
        }
    }

    public String convertPhoneNumber(String source) {
        source = source.trim();
        if (source.startsWith("+")) {
            source = source.substring(1);
        }
        if (source.startsWith("0")) {
            source = "84" + source.substring(1);
        } else {
            if (!source.startsWith("84")) {
                source = "84" + source;
            }
        }
        return source;
    }

    public String removeAccent(String accent) {
        return (StringUtils.replaceChars(
                StringUtils.replaceChars(
                        StringUtils.stripAccents(accent), (char) 273, (char) 100),
                (char) 272,
                (char) 68));
    }

    public void validateEmptyFields(String... fields) {
        for (String field : fields) {
            if (StringUtils.isEmpty(field)) {
                throw new BusinessException(ResponseStatusCodeEnum.BAD_REQUEST);
            }
        }
    }

    /**
     * 84916636395 -> 0916-636-395
     *
     * @param phoneNumber
     * @return
     */
    public String formatMsisdnWithDashed(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            log.warn("Null msisdn");
            throw new NullPointerException();
        }

        Pattern pattern = Pattern.compile("(84|0)([\\d]{3})([\\d]{3})([\\d]+)");

        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.find() && matcher.groupCount() != 4) {
            log.info("Msisdn {} is incorrect format", phoneNumber);
            throw new NullPointerException();
        }
        return StringUtils.join(0, matcher.group(2), "-", matcher.group(3), "-", matcher.group(4));
    }

    public boolean isPhoneNumber(String msisdn) {
        if (StringUtils.isBlank(msisdn)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(0|84)(\\d{9})\\b");

        Matcher matcher = pattern.matcher(msisdn);
        return matcher.find();
    }

    /**
     * Random an item in list with chances
     *
     * @param objects list of items
     * @param chances list of chances
     * @return item
     */
    public <T> T randomItems(List<T> objects, List<Integer> chances) {
        int poolSize = chances.stream().mapToInt(Integer::intValue).sum();

        // get a random number from 0 to size
        int rdNumber = RandomUtils.nextInt(0, poolSize) + 1;

        // Detect the item, which corresponds to current random number.
        int accumulatedProbability = 0;

        for (int i = 0; i < objects.size(); i++) {
            accumulatedProbability += chances.get(i);
            if (rdNumber <= accumulatedProbability) {
                return objects.get(i);
            }
        }
        return null; // never come
    }

    public Date parseDateOrDefault(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    public Date parseDateOrDefault(String str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    public String convertDateString(String date, String srcFormat, String desFormat) {
        return parseDateToString(parseDateOrDefault(date, srcFormat), desFormat);
    }

    public Date addDate(Date current, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    @SuppressWarnings("squid:S117")
    public String parseDateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat DateFor = new SimpleDateFormat(pattern);
            return DateFor.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer parseStringToInteger(String input, Integer defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Kiem tra thong tin obj 1 va 2 khac nhau ko. Neu obj 1 null, skip.
     *
     * @param objects
     * @return true neu ko thay doi gi
     */
    public boolean compareObject(Object... objects) {
        if (objects.length % 2 != 0) {
            log.error("anyChange() {} is not even number", objects.length);
            throw new NullPointerException();
        }
        for (int i = 0; i < objects.length; i += 2) {
            if (ObjectUtils.isEmpty(objects[i])) {
                continue;
            }
            if (!objects[i].equals(objects[i + 1])) {
                log.info("anyChange() {} <> {}", objects[i], objects[i + 1]);
                return true;
            }
        }
        return false;
    }

    public <T> T convertClass(Object input, Class<?> clazz) {
        return (T) objectMapper.convertValue(input, clazz);
    }

    public String genUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public String changeFormatDateString(String original, String formatOriginal, String newFormat) {
        if (StringUtils.isNotBlank(original) && StringUtils.isNotBlank(formatOriginal) && StringUtils.isNotBlank(newFormat)) {
            try {
                return parseDateToString(parseDateOrDefault(original, formatOriginal), newFormat);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public String formatMsisdnHidden(String msisdn) {
        if (StringUtils.isEmpty(msisdn))
            return msisdn;
        String regex = "(\\(*)(\\[*)(\\d{2})(\\d{3})(\\d{3})(\\d{3})(\\]*)(\\)*)";
        String replacement = "0$4-xxx-$6";
        return msisdn.replaceFirst(regex, replacement);
    }
}
