/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Person;
import org.neo4j.driver.Values;
import static org.neo4j.driver.Values.parameters;
import org.neo4j.driver.internal.value.NullValue;
import org.neo4j.driver.types.Node;

/**
 *
 * @author thanh
 */
public class PersonDAO {

    private Connection conSql;
    private String status = "";
    private Driver driver = null;

    private static volatile PersonDAO instance = null;

    /**
     * Constructs a PersonDAO instance with a connection to a Neo4j and MySQL
     * database.
     */
    private PersonDAO() {
        try {
            conSql = new DBContext().getConnection();
            driver = new Neo4jContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    /**
     * Retrieves an instance of PersonDAO, creating it if it doesn't already
     * exist.
     *
     * @return The singleton instance of the PersonDAO.
     */
    public static PersonDAO getInstance() {
        if (instance == null) {
            synchronized (PersonDAO.class) {
                if (instance == null) {
                    instance = new PersonDAO();
                }
            }
        }
        return instance;
    }

    public Map<String, List<String>> parentChildMap = new HashMap<>();
    public Map<Long, Person> peopleMap = new HashMap<>();

    /**
     * Retrieves the family tree by a given treeId. The tree is constructed
     * starting from the root person marked with 'root' in the optionalInfo
     * field.
     *
     * @param treeId The ID of the tree to retrieve.
     * @return The root Person of the family tree, or null if not found.
     */
    public Person getFamilyTreeByTreeId(String treeId) {
        peopleMap.clear();
        try (Session session = driver.session()) {
            String query = "MATCH (root:Person {treeId: $treeId, optionalInfo: 'root'})-[:PARENT_OF*0..]->(descendant) "
                    + "OPTIONAL MATCH (descendant)-[:PARENT_OF]->(child) "
                    + "OPTIONAL MATCH (descendant)-[:SPOUSE]-(spouse) "
                    + "WITH descendant, spouse, COLLECT(DISTINCT child) AS children, ID(descendant) AS descendantId, ID(spouse) AS spouseId "
                    + "RETURN descendant, descendantId, spouse, spouseId, children";
            try (Transaction tx = session.beginTransaction()) {
                Result result = tx.run(query, Values.parameters("treeId", treeId));
                while (result.hasNext()) {
                    Record record = result.next();
                    Node descendantNode = record.get("descendant").asNode();
                    int descendantId = record.get("descendantId").asInt(); 

                    var spouseValue = record.get("spouse");
                    int spouseId = -1; // Giá trị mặc định cho spouseId
                    if (!(spouseValue instanceof NullValue)) {
                        spouseId = record.get("spouseId").asInt(); 
                    }

                    List<Object> childrenNodes = record.get("children").asList();
                    Person descendantPerson = processPersonNode(descendantNode, descendantId); 

                    // Process spouse
                    if (!(spouseValue instanceof NullValue)) {
                        Node spouseNode = spouseValue.asNode();
                        Person spousePerson = processPersonNode(spouseNode, spouseId); 
                        descendantPerson.setSpouse(spousePerson);
                    }

                    // Process children
                    for (Object childObj : childrenNodes) {
                        if (childObj instanceof Node) {
                            Node childNode = (Node) childObj;
                            long childId = childNode.id();
                            Person childPerson = processPersonNode(childNode, childId); 
                            descendantPerson.addChild(childPerson);
                        }
                    }
                }

                tx.commit();
            }
        }

        return peopleMap.values()
                .stream()
                .filter(p -> p.getTreeId().equals(treeId) && "root".equals(p.getOptionalInfo()))
                .findFirst()
                .orElse(null);
    }

    public Person getFamilyTreeByNodeId(long nodeId) {
        peopleMap.clear();
        try (Session session = driver.session()) {
            
            String query = "MATCH (root:Person)-[:PARENT_OF*0..]->(descendant) WHERE ID(root) = $nodeId "
                    + "OPTIONAL MATCH (descendant)-[:PARENT_OF]->(child) "
                    + "OPTIONAL MATCH (descendant)-[:SPOUSE]-(spouse) "
                    + "WITH descendant, spouse, COLLECT(DISTINCT child) AS children, ID(descendant) AS descendantId, ID(spouse) AS spouseId "
                    + "RETURN descendant, descendantId, spouse, spouseId, children";

            try (Transaction tx = session.beginTransaction()) {
                Result result = tx.run(query, Values.parameters("nodeId", nodeId));
                while (result.hasNext()) {
                    Record record = result.next();
                    Node descendantNode = record.get("descendant").asNode();
                    int descendantId = record.get("descendantId").asInt(); 

                    var spouseValue = record.get("spouse");
                    int spouseId = -1; 
                    if (!(spouseValue instanceof NullValue)) {
                        spouseId = record.get("spouseId").asInt();
                    }

                    List<Object> childrenNodes = record.get("children").asList();
                    Person descendantPerson = processPersonNode(descendantNode, descendantId); 

                    
                    if (!(spouseValue instanceof NullValue)) {
                        Node spouseNode = spouseValue.asNode();
                        Person spousePerson = processPersonNode(spouseNode, spouseId); 
                        descendantPerson.setSpouse(spousePerson);
                    }

                    
                    for (Object childObj : childrenNodes) {
                        if (childObj instanceof Node) {
                            Node childNode = (Node) childObj;
                            long childId = childNode.id();
                            Person childPerson = processPersonNode(childNode, childId); 
                            descendantPerson.addChild(childPerson);
                        }
                    }
                }
                tx.commit();
            }
        }
        return peopleMap.get(nodeId);
    }

    private Person processPersonNode(Node node, long id) { 
        String name = node.get("name").asString();
        String birthDate = node.get("birthDate").asString();
        String deathDate = null;
        if (node.containsKey("deathDate")) {
            deathDate = node.get("deathDate").asString();
        }
        int userId = -1;
        if (node.containsKey("userId")) {
            userId = node.get("userId").asInt();
        }
        String information = null;
        if (node.containsKey("information")) {
            information = node.get("information").asString();
        }
        String optionalInfo = null;
        if (node.containsKey("optionalInfo")) {
            optionalInfo = node.get("optionalInfo").asString();
        }
        String treeId = node.get("treeId").asString();

        Person person = peopleMap.get(id);
        if (person == null) {
            person = new Person.Builder()
                    .id(id)
                    .userId(userId)
                    .name(name)
                    .birthDate(birthDate)
                    .deathDate(deathDate)
                    .information(information)
                    .optionalInfo(optionalInfo)
                    .treeId(treeId)
                    .build();

            peopleMap.put(id, person);
        }

        return person;
    }

    /**
     * Adds a child to a parent in the family tree.
     *
     * @param userId
     * @param name The name of the child.
     * @param birthDate The birth date of the child.
     * @param deathDate The death date of the child (if applicable).
     * @param information
     * @param treeId The ID of the family tree.
     * @param parentName The name of the parent.
     * @param parentBirthDate The birth date of the parent.
     */
    public boolean addChild(Long parentNodeId, Integer childUserId, String name, String birthDate, String deathDate, String information, String treeId, String parentBirthDate) {
        try (Session session = driver.session()) {
            if (!isUserIdAvailableInTree(childUserId, treeId) && childUserId != -1) {
                return false;
            }
            if (birthDate.equalsIgnoreCase(parentBirthDate)) {
                return false;
            }

            String parentMatchClause = "MATCH (parent:Person) WHERE ID(parent) = $parentNodeId ";

            String childCreateClause = "CREATE (child:Person {userId: $childUserId, name: $name, birthDate: $birthDate, deathDate: $deathDate, information: $information, treeId: $treeId}) ";

            String query = parentMatchClause + childCreateClause
                    + "MERGE (parent)-[:PARENT_OF]->(child) "
                    + "WITH parent, child "
                    + "OPTIONAL MATCH (parent)-[:SPOUSE]-(spouse) "
                    + "WHERE NOT (spouse)-[:PARENT_OF]->(child) "
                    + "FOREACH (s IN CASE WHEN spouse IS NOT NULL THEN [spouse] ELSE [] END | "
                    + "MERGE (s)-[:PARENT_OF]->(child))";

            session.run(query, Values.parameters(
                    "parentNodeId", parentNodeId,
                    "childUserId", childUserId,
                    "parentBirthDate", parentBirthDate,
                    "treeId", treeId,
                    "name", name,
                    "birthDate", birthDate,
                    "deathDate", deathDate,
                    "information", information
            ));
        }
        return true;
    }

    public Long addChildReturn(Long parentNodeId, Integer childUserId, String name, String birthDate, String deathDate, String information, String treeId) {
        try (Session session = driver.session()) {
            if (!isUserIdAvailableInTree(childUserId, treeId) && childUserId != -1) {
                return (long) -1;
            }

            String parentMatchClause = "MATCH (parent:Person) WHERE ID(parent) = $parentNodeId ";

            String childCreateClause = "CREATE (child:Person {userId: $childUserId, name: $name, birthDate: $birthDate, deathDate: $deathDate, information: $information, treeId: $treeId}) ";

            String query = parentMatchClause + childCreateClause
                    + "MERGE (parent)-[:PARENT_OF]->(child) "
                    + "WITH parent, child "
                    + "OPTIONAL MATCH (parent)-[:SPOUSE]-(spouse) "
                    + "WHERE NOT (spouse)-[:PARENT_OF]->(child) "
                    + "FOREACH (s IN CASE WHEN spouse IS NOT NULL THEN [spouse] ELSE [] END | "
                    + "MERGE (s)-[:PARENT_OF]->(child)) "
                    + "RETURN ID(child) AS childId";

            Result result = session.run(query, Values.parameters(
                    "parentNodeId", parentNodeId,
                    "childUserId", childUserId,
                    "treeId", treeId,
                    "name", name,
                    "birthDate", birthDate,
                    "deathDate", deathDate,
                    "information", information
            ));

            if (result.hasNext()) {
                Record record = result.next();
                return record.get("childId").asLong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) -1;
    }

    /**
     * Adds a spouse relationship between two people in the family tree.
     *
     * @param userId1
     * @param userId2
     * @param treeId The ID of the family tree.
     * @param name1
     * @param birthDate1
     * @param information1
     * @param deathDate1
     * @param birthDate2
     * @param name2
     * @param information2
     * @param deathDate2
     */
    public boolean addSpouse2(int userIdSpouse, Integer userIdInTree, String name1, String birthDate1, String deathDate1, String information1, String treeId, String name2, String birthDate2) {
        try (Session session = driver.session()) {
            if (!isUserIdAvailableInTree(userIdSpouse, treeId) && userIdSpouse != -1) {
                return false;
            }
            String ensureNode1ExistsQuery = userIdSpouse == -1
                    ? "MERGE (p1:Person {name: $name1, birthDate: $birthDate1, treeId: $treeId}) ON CREATE SET p1.deathDate = $deathDate1, p1.information = $information1"
                    : "MERGE (p1:Person {userId: $userId1, treeId: $treeId}) ON CREATE SET p1.name = $name1, p1.birthDate = $birthDate1, p1.deathDate = $deathDate1, p1.information = $information1";

            session.run(ensureNode1ExistsQuery, Values.parameters(
                    "userId1", userIdSpouse,
                    "name1", name1,
                    "birthDate1", birthDate1,
                    "deathDate1", deathDate1,
                    "information1", information1,
                    "treeId", treeId
            ));

            String ensureNode2Exist = userIdInTree != null && userIdInTree != -1
                    ? "MERGE (p2:Person {name: $name2, birthDate: $birthDate2, treeId: $treeId}) ON CREATE SET p2.userId = $userId2 ON MATCH SET p2.userId = CASE WHEN $userId2 <> -1 THEN $userId2 ELSE p2.userId END"
                    : "MERGE (p2:Person {name: $name2, birthDate: $birthDate2, treeId: $treeId})";

            session.run(ensureNode2Exist, Values.parameters(
                    "name2", name2,
                    "birthDate2", birthDate2,
                    "treeId", treeId,
                    "userId2", userIdInTree
            ));

            String createSpouseRelationshipQuery = "MATCH (p1:Person), (p2:Person {name: $name2, birthDate: $birthDate2, treeId: $treeId}) "
                    + "WHERE ($userId1 = -1 AND p1.name = $name1 AND p1.birthDate = $birthDate1 AND p1.treeId = $treeId) "
                    + "OR ($userId1 <> -1 AND p1.userId = $userId1 AND p1.treeId = $treeId) "
                    + "MERGE (p1)-[:SPOUSE]->(p2) "
                    + "MERGE (p2)-[:SPOUSE]->(p1)";

            session.run(createSpouseRelationshipQuery, Values.parameters(
                    "userId1", userIdSpouse,
                    "name1", name1,
                    "birthDate1", birthDate1,
                    "treeId", treeId,
                    "name2", name2,
                    "birthDate2", birthDate2
            ));

            String createParentChildRelationshipQuery = "MATCH (p1:Person), (p2:Person {name: $name2, birthDate: $birthDate2, treeId: $treeId})-[:PARENT_OF]->(child) "
                    + "WHERE ($userId1 = -1 AND p1.name = $name1 AND p1.birthDate = $birthDate1 AND p1.treeId = $treeId) "
                    + "OR ($userId1 <> -1 AND p1.userId = $userId1 AND p1.treeId = $treeId) "
                    + "MERGE (p1)-[:PARENT_OF]->(child)";

            session.run(createParentChildRelationshipQuery, Values.parameters(
                    "userId1", userIdSpouse,
                    "name1", name1,
                    "birthDate1", birthDate1,
                    "treeId", treeId,
                    "name2", name2,
                    "birthDate2", birthDate2
            ));
        }
        return true;
    }

    public boolean addSpouse(Integer userIdSpouse, Long nodeIdInTree, String name1, String birthDate1, String deathDate1, String information1, String treeId) {
        try (Session session = driver.session()) {
            if (!isUserIdAvailableInTree(userIdSpouse, treeId) && userIdSpouse != -1) {
                return false;
            }

            String checkSpouseExistenceQuery = "MATCH (existingPerson)-[:SPOUSE]-() WHERE ID(existingPerson) = $nodeIdInTree RETURN COUNT(*) AS spouseCount";
            Long spouseCount = session.readTransaction(tx -> {
                Result result = tx.run(checkSpouseExistenceQuery, Values.parameters(
                        "nodeIdInTree", nodeIdInTree
                ));
                return result.single().get("spouseCount").asLong();
            });

            if (spouseCount > 0) {
                return false;
            }

            String createSpouseNodeQuery = "CREATE (spouse:Person {userId: $userId1, name: $name1, birthDate: $birthDate1, deathDate: $deathDate1, information: $information1, treeId: $treeId}) RETURN ID(spouse) AS spouseNodeId";

            Long spouseNodeId = session.writeTransaction(tx -> {
                Result result = tx.run(createSpouseNodeQuery, Values.parameters(
                        "userId1", userIdSpouse,
                        "name1", name1,
                        "birthDate1", birthDate1,
                        "deathDate1", deathDate1,
                        "information1", information1,
                        "treeId", treeId
                ));
                return result.single().get("spouseNodeId").asLong();
            });

            String createSpouseRelationshipQuery = "MATCH (spouse:Person), (existingPerson) WHERE ID(spouse) = $spouseNodeId AND ID(existingPerson) = $nodeIdInTree "
                    + "CREATE (spouse)-[:SPOUSE]->(existingPerson), "
                    + "(existingPerson)-[:SPOUSE]->(spouse)";

            session.run(createSpouseRelationshipQuery, Values.parameters(
                    "spouseNodeId", spouseNodeId,
                    "nodeIdInTree", nodeIdInTree
            ));

            String createParentChildRelationshipQuery = "MATCH (spouse:Person), (existingPerson)-[:PARENT_OF]->(child) WHERE ID(spouse) = $spouseNodeId AND ID(existingPerson) = $nodeIdInTree "
                    + "CREATE (spouse)-[:PARENT_OF]->(child)";

            session.run(createParentChildRelationshipQuery, Values.parameters(
                    "spouseNodeId", spouseNodeId,
                    "nodeIdInTree", nodeIdInTree
            ));

        }
        return true;
    }

    /**
     * Updates the information of a person in the family tree.
     *
     * @param userId
     * @param oldName The current name of the person.
     * @param oldBirthDate The current birth date of the person.
     * @param treeId The ID of the family tree.
     * @param newName The new name of the person.
     * @param newBirthDate The new birth date of the person.
     * @param newInformation
     * @param newDeathDate The new death date of the person (if applicable).
     */
    public boolean updatePerson(Long nodeId, Integer newUserId, String newName, String newBirthDate, String newDeathDate, String newInformation, String treeId) {
        try (Session session = driver.session()) {
            if (!isUserIdAvailableInTree(newUserId, treeId) && newUserId != -1) {
                return false;
            }
            
            String matchClause = "MATCH (p:Person) WHERE ID(p) = $nodeId ";

            String userIdSetClause = (newUserId != null && newUserId != -1)
                    ? ", p.userId = $newUserId "
                    : "";

            String query = matchClause
                    + "SET p.name = $newName, p.birthDate = $newBirthDate, "
                    + "p.deathDate = $newDeathDate, p.information = $newInformation"
                    + userIdSetClause;

            session.run(query, Values.parameters(
                    "nodeId", nodeId,
                    "newUserId", newUserId,
                    "newName", newName,
                    "newBirthDate", newBirthDate,
                    "newDeathDate", newDeathDate,
                    "newInformation", newInformation,
                    "treeId", treeId
            ));
        }
        return true;
    }

    /**
     * Deletes a person and their descendants from the family tree. This method
     * only deletes descendants that have no remaining parents in the tree.
     *
     * @param name The name of the person to delete.
     * @param birthDate The birth date of the person to delete.
     * @param treeId The ID of the family tree.
     */
    public void removePerson(Long nodeId, String treeId) {
        try (Session session = driver.session()) {
            String matchClause = "MATCH (p:Person) WHERE ID(p) = $nodeId ";

            String deleteQuery = matchClause + "DETACH DELETE p";
            session.run(deleteQuery, Values.parameters("nodeId", nodeId));

            removeOrphanNodes(treeId);
        }
    }

    public void removeOrphanNodes(String treeId) {
        try (Session session = driver.session()) {
            int affectedRows;
            do {
                String query = "MATCH (c:Person {treeId: $treeId}) "
                        + "WHERE NOT (c)<-[:PARENT_OF]-(:Person) "
                        + "AND NOT (c)-[:SPOUSE]-(:Person {optionalInfo: 'root'}) "
                        + "AND NOT ((c)-[:SPOUSE]-(:Person)<-[:PARENT_OF]-()) "
                        + "AND coalesce(c.optionalInfo, '') <> 'root' "
                        + "WITH c LIMIT 10000 "
                        + "DETACH DELETE c "
                        + "RETURN count(c) AS deletedCount";
                Result result = session.run(query, Values.parameters("treeId", treeId));
                affectedRows = result.single().get("deletedCount").asInt();
            } while (affectedRows > 0);
        }
    }

    /**
     * Creates a new family tree with a specified root person. The root person
     * is marked with 'root' in the optionalInfo field. This method checks if a
     * tree with the given treeId already exists before creation.
     *
     * @param treeId The ID for the new tree.
     * @param rootName The name of the root person.
     * @param rootBirthDate The birth date of the root person.
     * @param rootDeathDate The death date of the root person (if applicable).
     * @param rootOptionalInfo Additional information for the root person.
     * @return true if the tree was created successfully, false if a tree with
     * the given treeId already exists.
     */
    public boolean isAnyNodeExist(String treeId) {
        try (Session session = driver.session()) {
            String checkQuery = "MATCH(p:Person {treeId: $ treeId}) RETURN p LIMIT 1";
            var result = session.run(checkQuery, Values.parameters("treeId", treeId));
            if (result.hasNext()) {
                return true;
            }
        }
        return false;
    }

    public boolean createNewFamilyTree(String treeId, String rootName, String rootBirthDate, String rootDeathDate, String rootOptionalInfo, Integer userId) {
        try (Session session = driver.session()) {
            
            String checkQuery = "MATCH (p:Person {treeId: $treeId}) RETURN p LIMIT 1";
            var result = session.run(checkQuery, Values.parameters("treeId", treeId));
            if (result.hasNext()) {
                
                return false;
            }

            
            String createQuery = "CREATE (root:Person {name: $name, birthDate: $birthDate, deathDate: $deathDate, information: $information, optionalInfo: $optionalInfo, treeId: $treeId"
                    + (userId != null ? ", userId: $userId" : "") + "})";
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", rootName);
            parameters.put("birthDate", rootBirthDate);
            parameters.put("deathDate", rootDeathDate);
            parameters.put("information", rootOptionalInfo);
            parameters.put("optionalInfo", "root");
            parameters.put("treeId", treeId);
            if (userId != null) {
                parameters.put("userId", userId);
            }

            
            session.run(createQuery, parameters);

            
            return true;
        }
    }

    public Integer createTreeForUser(int userId) {
        String checkQuery = "SELECT tree_id FROM tree WHERE user_id = ?";
        String insertQuery = "INSERT INTO tree (user_id) VALUES (?)";

        try (PreparedStatement psCheck = conSql.prepareStatement(checkQuery)) {
            psCheck.setInt(1, userId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                return rs.getInt("tree_id");
            }

            try (PreparedStatement psInsert = conSql.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                psInsert.setInt(1, userId);
                int affectedRows = psInsert.executeUpdate();

                if (affectedRows > 0) {
                    ResultSet generatedKeys = psInsert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            status = "Error at createTreeForUser: " + e.getMessage();
        }
        return null;
    }

    public Integer createTreeForFamily(int familyId) {
        String checkQuery = "SELECT tree_id FROM tree WHERE family_id = ?";
        String insertQuery = "INSERT INTO tree (family_id) VALUES (?)";

        try (PreparedStatement psCheck = conSql.prepareStatement(checkQuery)) {
            psCheck.setInt(1, familyId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                return rs.getInt("tree_id");
            }

            try (PreparedStatement psInsert = conSql.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                psInsert.setInt(1, familyId);
                int affectedRows = psInsert.executeUpdate();

                if (affectedRows > 0) {
                    ResultSet generatedKeys = psInsert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            
            System.err.println("Error at createTreeForFamily: " + e.getMessage());
        }
        return null;
    }

    public boolean isUserIdAvailableInTree(int userId, String treeId) {
        try (Session session = driver.session()) {
            if (userId == -1) {
                return true;
            }
            String query = "MATCH (n) WHERE n.userId = $userId AND n.treeId = $treeId RETURN n LIMIT 1";
            Result result = session.run(query, Values.parameters("userId", userId, "treeId", treeId));

            
            return !result.hasNext(); 
        }
    }

    public boolean graftTree(long nodeTarget, long nodeMy) {
        try (Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                Person p = getPersonByNodeId(nodeTarget);
                long nodeChild = addChildReturn(nodeMy, -1, p.getName(), p.getBirthDate(), p.getDeathDate(), p.getInformation(), p.getTreeId());

                if (nodeChild == -1) {
                    return false;
                }
                String graftQuery = "MATCH (rootA:Person) WHERE ID(rootA) = $rootANodeId "
                        + "MATCH (rootB:Person) WHERE ID(rootB) = $rootBNodeId "
                        + "MATCH path = (rootA)-[:PARENT_OF*0..]->(node)-[:SPOUSE*0..1]-(spouse) "
                        + "WITH rootA, rootB, collect(path) as paths "
                        + "CALL apoc.refactor.cloneSubgraphFromPaths(paths, {standinNodes: [[rootA, rootB]]}) "
                        + "YIELD input, output "
                        + "WITH rootA, rootB, collect(output) AS clonedNodes "
                        + "UNWIND clonedNodes AS clonedNode "
                        + "MATCH (n) WHERE ID(n) = ID(clonedNode) "
                        + "SET n.treeId = rootB.treeId "
                        + "RETURN count(n) AS count";
                Result graftResult = tx.run(graftQuery,
                        java.util.Map.of("rootANodeId", nodeTarget,
                                "rootBNodeId", nodeChild));
                int count = graftResult.single().get("count").asInt();
                tx.commit();
                return count > 0;
            }
        }
    }

    public Person getPersonByNodeId(long nodeId) {
        try (Session session = driver.session()) {
            String query = "MATCH (person:Person) WHERE ID(person) = $nodeId RETURN person";
            Record record = session.run(query, Values.parameters("nodeId", nodeId)).single();
            Node personNode = record.get("person").asNode();

            return processPersonNode(personNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Person processPersonNode(Node personNode) {
        Person person = new Person.Builder()
                .id(personNode.id())
                .userId(personNode.get("userId").asInt())
                .name(personNode.get("name").asString())
                .birthDate(personNode.get("birthDate").asString(null))
                .deathDate(personNode.get("deathDate").asString(null))
                .information(personNode.get("information").asString(null))
                .optionalInfo(personNode.get("optionalInfo").asString(null))
                .treeId(personNode.get("treeId").asString())
                .build();

        return person;
    }

//    public static void main(String... args) {
//        PersonDAO.getInstance().graftTree(20, 77);
//
//    }
}
