import ExpoTiktokBiz from "expo-tiktok-biz";
import { useEffect } from "react";
import { SafeAreaView, ScrollView, Text } from "react-native";
export default function App() {
  useEffect(() => {
    ExpoTiktokBiz.initialize("6741689550", "7481118327511711761");
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.container}>
        <Text style={styles.header}>Module API Example</Text>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = {
  header: {
    fontSize: 30,
    margin: 20,
  },
  groupHeader: {
    fontSize: 20,
    marginBottom: 20,
  },
  group: {
    margin: 20,
    backgroundColor: "#fff",
    borderRadius: 10,
    padding: 20,
  },
  container: {
    flex: 1,
    backgroundColor: "#eee",
  },
  view: {
    flex: 1,
    height: 200,
  },
};
