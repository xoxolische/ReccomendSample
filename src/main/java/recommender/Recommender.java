package recommender;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Recommender {
    private DataModel datamodel;
    private UserSimilarity similarity;
    private UserNeighborhood neighborhood;
    private UserBasedRecommender recommender;

    public Recommender() {
        setDataModel();
        setSimilarity();
        setUserNeighborhood();
        recommender = new GenericUserBasedRecommender(datamodel, neighborhood, similarity);
    }

    private void setDataModel() {
        try {
            datamodel = new FileDataModel(new File(".//data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setSimilarity() {
        try {
            similarity = new PearsonCorrelationSimilarity(datamodel);
        } catch (TasteException e) {
            e.printStackTrace();
        }

    }

    private void setUserNeighborhood() {
        neighborhood = new ThresholdUserNeighborhood(3.0, similarity, datamodel);
    }

    public void recommendToUser(long userId, int number){
        List<RecommendedItem> recommendations = null;
        try {
            recommendations = recommender.recommend(userId, number);
        } catch (TasteException e) {
            e.printStackTrace();
        }

        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }
    }
}