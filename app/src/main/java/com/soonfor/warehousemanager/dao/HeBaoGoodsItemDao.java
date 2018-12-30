package com.soonfor.warehousemanager.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.soonfor.warehousemanager.module.instore.beans.hebao.HeBaoGoodsItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HeBaoGoodsItem".
*/
public class HeBaoGoodsItemDao extends AbstractDao<HeBaoGoodsItem, Long> {

    public static final String TABLENAME = "HeBaoGoodsItem";

    /**
     * Properties of entity HeBaoGoodsItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property FOrdNo = new Property(1, String.class, "fOrdNo", false, "fOrdNo");
        public final static Property FSplitBatchNo = new Property(2, String.class, "fSplitBatchNo", false, "fSplitBatchNo");
        public final static Property FPackNo = new Property(3, String.class, "fPackNo", false, "fPackNo");
        public final static Property FGoodsID = new Property(4, String.class, "fGoodsID", false, "fGoodsID");
        public final static Property FGoodsCode = new Property(5, String.class, "fGoodsCode", false, "fGoodsCode");
        public final static Property FGoodsName = new Property(6, String.class, "fGoodsName", false, "fGoodsName");
        public final static Property FSizeDesc = new Property(7, String.class, "fSizeDesc", false, "fSizeDesc");
        public final static Property FCstLotNo = new Property(8, String.class, "fCstLotNo", false, "fCstLotNo");
        public final static Property FBelongGoodsID = new Property(9, String.class, "fBelongGoodsID", false, "fBelongGoodsID");
        public final static Property FBelongGoodsCode = new Property(10, String.class, "fBelongGoodsCode", false, "fBelongGoodsCode");
        public final static Property FBelongCstLotNo = new Property(11, String.class, "fBelongCstLotNo", false, "fBelongCstLotNo");
        public final static Property FOrdSpID = new Property(12, String.class, "fOrdSpID", false, "fOrdSpID");
    }


    public HeBaoGoodsItemDao(DaoConfig config) {
        super(config);
    }
    
    public HeBaoGoodsItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HeBaoGoodsItem\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"fOrdNo\" TEXT," + // 1: fOrdNo
                "\"fSplitBatchNo\" TEXT," + // 2: fSplitBatchNo
                "\"fPackNo\" TEXT," + // 3: fPackNo
                "\"fGoodsID\" TEXT," + // 4: fGoodsID
                "\"fGoodsCode\" TEXT," + // 5: fGoodsCode
                "\"fGoodsName\" TEXT," + // 6: fGoodsName
                "\"fSizeDesc\" TEXT," + // 7: fSizeDesc
                "\"fCstLotNo\" TEXT," + // 8: fCstLotNo
                "\"fBelongGoodsID\" TEXT," + // 9: fBelongGoodsID
                "\"fBelongGoodsCode\" TEXT," + // 10: fBelongGoodsCode
                "\"fBelongCstLotNo\" TEXT," + // 11: fBelongCstLotNo
                "\"fOrdSpID\" TEXT);"); // 12: fOrdSpID
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HeBaoGoodsItem\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HeBaoGoodsItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fOrdNo = entity.getFOrdNo();
        if (fOrdNo != null) {
            stmt.bindString(2, fOrdNo);
        }
 
        String fSplitBatchNo = entity.getFSplitBatchNo();
        if (fSplitBatchNo != null) {
            stmt.bindString(3, fSplitBatchNo);
        }
 
        String fPackNo = entity.getFPackNo();
        if (fPackNo != null) {
            stmt.bindString(4, fPackNo);
        }
 
        String fGoodsID = entity.getFGoodsID();
        if (fGoodsID != null) {
            stmt.bindString(5, fGoodsID);
        }
 
        String fGoodsCode = entity.getFGoodsCode();
        if (fGoodsCode != null) {
            stmt.bindString(6, fGoodsCode);
        }
 
        String fGoodsName = entity.getFGoodsName();
        if (fGoodsName != null) {
            stmt.bindString(7, fGoodsName);
        }
 
        String fSizeDesc = entity.getFSizeDesc();
        if (fSizeDesc != null) {
            stmt.bindString(8, fSizeDesc);
        }
 
        String fCstLotNo = entity.getFCstLotNo();
        if (fCstLotNo != null) {
            stmt.bindString(9, fCstLotNo);
        }
 
        String fBelongGoodsID = entity.getFBelongGoodsID();
        if (fBelongGoodsID != null) {
            stmt.bindString(10, fBelongGoodsID);
        }
 
        String fBelongGoodsCode = entity.getFBelongGoodsCode();
        if (fBelongGoodsCode != null) {
            stmt.bindString(11, fBelongGoodsCode);
        }
 
        String fBelongCstLotNo = entity.getFBelongCstLotNo();
        if (fBelongCstLotNo != null) {
            stmt.bindString(12, fBelongCstLotNo);
        }
 
        String fOrdSpID = entity.getFOrdSpID();
        if (fOrdSpID != null) {
            stmt.bindString(13, fOrdSpID);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HeBaoGoodsItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fOrdNo = entity.getFOrdNo();
        if (fOrdNo != null) {
            stmt.bindString(2, fOrdNo);
        }
 
        String fSplitBatchNo = entity.getFSplitBatchNo();
        if (fSplitBatchNo != null) {
            stmt.bindString(3, fSplitBatchNo);
        }
 
        String fPackNo = entity.getFPackNo();
        if (fPackNo != null) {
            stmt.bindString(4, fPackNo);
        }
 
        String fGoodsID = entity.getFGoodsID();
        if (fGoodsID != null) {
            stmt.bindString(5, fGoodsID);
        }
 
        String fGoodsCode = entity.getFGoodsCode();
        if (fGoodsCode != null) {
            stmt.bindString(6, fGoodsCode);
        }
 
        String fGoodsName = entity.getFGoodsName();
        if (fGoodsName != null) {
            stmt.bindString(7, fGoodsName);
        }
 
        String fSizeDesc = entity.getFSizeDesc();
        if (fSizeDesc != null) {
            stmt.bindString(8, fSizeDesc);
        }
 
        String fCstLotNo = entity.getFCstLotNo();
        if (fCstLotNo != null) {
            stmt.bindString(9, fCstLotNo);
        }
 
        String fBelongGoodsID = entity.getFBelongGoodsID();
        if (fBelongGoodsID != null) {
            stmt.bindString(10, fBelongGoodsID);
        }
 
        String fBelongGoodsCode = entity.getFBelongGoodsCode();
        if (fBelongGoodsCode != null) {
            stmt.bindString(11, fBelongGoodsCode);
        }
 
        String fBelongCstLotNo = entity.getFBelongCstLotNo();
        if (fBelongCstLotNo != null) {
            stmt.bindString(12, fBelongCstLotNo);
        }
 
        String fOrdSpID = entity.getFOrdSpID();
        if (fOrdSpID != null) {
            stmt.bindString(13, fOrdSpID);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public HeBaoGoodsItem readEntity(Cursor cursor, int offset) {
        HeBaoGoodsItem entity = new HeBaoGoodsItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fOrdNo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // fSplitBatchNo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // fPackNo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // fGoodsID
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // fGoodsCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // fGoodsName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // fSizeDesc
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // fCstLotNo
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // fBelongGoodsID
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // fBelongGoodsCode
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // fBelongCstLotNo
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // fOrdSpID
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HeBaoGoodsItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFOrdNo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFSplitBatchNo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFPackNo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFGoodsID(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFGoodsCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFGoodsName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFSizeDesc(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFCstLotNo(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFBelongGoodsID(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFBelongGoodsCode(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setFBelongCstLotNo(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFOrdSpID(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HeBaoGoodsItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HeBaoGoodsItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HeBaoGoodsItem entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
