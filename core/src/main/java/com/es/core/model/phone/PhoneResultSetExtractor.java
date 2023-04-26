package com.es.core.model.phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {

    @Override
    public List<Phone> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Phone> phones = new ArrayList<>();

        boolean isDataPresent = rs.next();
        if (isDataPresent) {
            while (!rs.isAfterLast()) {
                Phone phone = new Phone();

                phone.setId(rs.getLong("id"));
                phone.setAnnounced(rs.getDate("announced"));
                phone.setBackCameraMegapixels(rs.getBigDecimal("backCameraMegapixels"));
                phone.setModel(rs.getString("model"));
                phone.setBrand(rs.getString("brand"));
                phone.setPrice(rs.getBigDecimal("price"));
                phone.setDisplaySizeInches(rs.getBigDecimal("displaySizeInches"));
                phone.setWeightGr(rs.getInt("weightGr"));
                phone.setLengthMm(rs.getBigDecimal("lengthMm"));
                phone.setWidthMm(rs.getBigDecimal("widthMm"));
                phone.setHeightMm(rs.getBigDecimal("heightMm"));
                phone.setDeviceType(rs.getString("deviceType"));
                phone.setOs(rs.getString("os"));
                phone.setDisplayResolution(rs.getString("displayResolution"));
                phone.setPixelDensity(rs.getInt("pixelDensity"));
                phone.setDisplayTechnology(rs.getString("displayTechnology"));
                phone.setFrontCameraMegapixels(rs.getBigDecimal("frontCameraMegapixels"));
                phone.setRamGb(rs.getBigDecimal("ramGb"));
                phone.setInternalStorageGb(rs.getBigDecimal("internalStorageGb"));
                phone.setBatteryCapacityMah(rs.getInt("batteryCapacityMah"));
                phone.setTalkTimeHours(rs.getBigDecimal("talkTimeHours"));
                phone.setStandByTimeHours(rs.getBigDecimal("standByTimeHours"));
                phone.setBluetooth(rs.getString("bluetooth"));
                phone.setPositioning(rs.getString("positioning"));
                phone.setImageUrl(rs.getString("imageUrl"));
                phone.setDescription(rs.getString("description"));

                Set<Color> colors = new HashSet<>();
                do {
                    Long colorId = rs.getLong("colorId");
                    if (colorId > 0) {
                        colors.add(new Color(colorId, rs.getString("colorCode")));
                    }
                } while (rs.next() && rs.getLong("id") == phone.getId());

                phone.setColors(colors);
                phones.add(phone);
            }
        }

        return phones;
    }

}